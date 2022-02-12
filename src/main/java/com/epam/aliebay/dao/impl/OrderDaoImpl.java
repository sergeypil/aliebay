package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderItem;
import com.epam.aliebay.exception.OrderNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class OrderDaoImpl extends AbstractBaseDao implements OrderDao {
    private static final ResultSetHandler<Order> ORDER_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<Order>> ORDERS_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<OrderItem>> ORDER_ITEMS_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_ITEM_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Long> LONG_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getLongResultSetHandler();

    private static final String SELECT_ALL_ORDERS_QUERY = "SELECT o.id AS order_id, o.user_id, o.created, o.cost, o.address, " +
            "order_status_id, osd.name, os.explanation FROM shop_order o INNER JOIN order_status os ON o.order_status_id = os.id " +
            "LEFT JOIN order_status_detail osd ON os.id = osd.id LEFT JOIN language l ON osd.language_id = l.id WHERE " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY o.created DESC";
    private static final String SELECT_ORDERS_BY_ID_USER_QUERY = "SELECT o.id AS order_id, o.user_id, o.created, o.cost, " +
            "o.address, order_status_id, osd.name, os.explanation FROM shop_order o INNER JOIN order_status os ON " +
            "o.order_status_id = os.id LEFT JOIN order_status_detail osd ON os.id = osd.id " +
            "LEFT JOIN language l ON osd.language_id = l.id WHERE o.user_id = ? AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY o.created DESC";
    private static final String SELECT_ORDER_BY_ID_QUERY = "SELECT o.id AS order_id, o.user_id, o.created, o.cost, " +
            "o.address, order_status_id, osd.name, os.explanation FROM shop_order o INNER JOIN order_status os ON " +
            "o.order_status_id = os.id LEFT JOIN order_status_detail osd ON os.id = osd.id " +
            "LEFT JOIN language l ON osd.language_id = l.id WHERE o.id = ?  AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?)";
    private static final String SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY = "SELECT oi.id AS order_item_id, oi.order_id, " +
            "oi.product_id, p.name, p.description, p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "ci.name AS category_name, c.image AS category_image, c.parent_id, ci.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name, oi.count AS count_order_item, retained_product_price FROM order_item oi " +
            "INNER JOIN product p ON oi.product_id = p.id INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail ci ON c.id = ci.id " +
            "INNER JOIN language l ON ci.language_id = l.id WHERE ci.language_id = 2 AND order_id = ?";
    private static final String INSERT_ORDER_QUERY = "INSERT INTO shop_order (user_id, created, order_status_id, cost, address) " +
            "VALUES (?,?,?,?,?)";
    private static final String UPDATE_ORDER_QUERY = "UPDATE shop_order SET user_id = ?, created = ?, order_status_id = ?, " +
            "cost = ?, address = ? WHERE id = ?";
    private static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_item (order_id, product_id, count, retained_product_price) " +
            "VALUES (?,?,?,?)";
    private static final String UPDATE_PRODUCT_REDUCE_COUNT_QUERY = "UPDATE product SET count = count - ? WHERE id = ?";
    private static final String UPDATE_PRODUCT_INCREASE_COUNT_QUERY = "UPDATE product SET count = count + ? WHERE id = ?";

    @Override
    public Optional<Order> getOrderById(long id, String language) {
        Order order = Optional.ofNullable(JdbcTemplate.select(SELECT_ORDER_BY_ID_QUERY, ORDER_RESULT_SET_HANDLER, id, language))
                .orElseThrow(() -> new OrderNotFoundException("Cannot find order with id = " + id));
        List<OrderItem> orderItems = JdbcTemplate.select(SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY,
                ORDER_ITEMS_RESULT_SET_HANDLER, order.getId());
        order.setItems(orderItems);
        return Optional.of(order);
    }

    @Override
    public List<Order> getAllOrders(String language) {
        List<Order> orders = JdbcTemplate.select(SELECT_ALL_ORDERS_QUERY,
                ORDERS_RESULT_SET_HANDLER, language);
        orders = orders.stream()
                .distinct()
                .peek(order -> order.setItems(JdbcTemplate.select(SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY,
                        ORDER_ITEMS_RESULT_SET_HANDLER, order.getId())))
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public List<Order> getOrdersByIdUser(int idUser, String language) {
        List<Order> orders = JdbcTemplate.select(SELECT_ORDERS_BY_ID_USER_QUERY, ORDERS_RESULT_SET_HANDLER, idUser,
                language);
        orders = orders.stream()
                .distinct()
                .peek(order -> order.setItems(JdbcTemplate.select(SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY,
                        ORDER_ITEMS_RESULT_SET_HANDLER, order.getId())))
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public void saveOrder(Order order) {
        doInTransaction(connection -> {
            long idOrder = JdbcTemplate.executeUpdateInTransactionWithGenKeys(connection, INSERT_ORDER_QUERY, LONG_RESULT_SET_HANDLER,
                    order.getUserId(), order.getCreated(), order.getStatus().getId(), order.getCost(), order.getAddress());
            order.getItems().stream()
                    .peek(el -> el.setOrderId(idOrder))
                    .forEach(el -> {
                        JdbcTemplate.executeUpdateInTransactionWithGenKeys(connection, INSERT_ORDER_ITEM_QUERY, LONG_RESULT_SET_HANDLER, el.getOrderId(),
                                el.getProduct().getId(), el.getCount(), el.getRetainedProductPrice());
                        JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_PRODUCT_REDUCE_COUNT_QUERY,
                                el.getCount(), el.getProduct().getId());
                    });
        });
    }

    @Override
    public void updateOrderStatusInOrder(Order order, long id) {
        JdbcTemplate.executeUpdate(UPDATE_ORDER_QUERY, order.getUserId(),
                order.getCreated(), order.getStatus().getId(), order.getCost(), order.getAddress(), id);
    }

    @Override
    public void updateOrderStatusAndRecalculateCountOfProducts(Order order, long id) {
        doInTransaction(connection -> {
            JdbcTemplate.executeUpdate(UPDATE_ORDER_QUERY, order.getUserId(),
                    order.getCreated(), order.getStatus().getId(), order.getCost(), order.getAddress(), id);
            order.getItems()
                    .forEach(el -> JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_PRODUCT_INCREASE_COUNT_QUERY,
                            el.getCount(), el.getProduct().getId()));
        });
    }
}
