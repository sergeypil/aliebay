package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderItem;
import com.epam.aliebay.exception.OrderNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class OrderDaoImpl extends AbstractBaseDao implements OrderDao {
    private static final ResultSetHandler<Order> orderResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<Order>> ordersResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<OrderItem>> orderItemsResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_ITEM_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Long> idResultSetHandler =
            ResultSetHandlerFactory.getLongResultSetHandler();

    private static final String SELECT_ALL_ORDERS_QUERY = "SELECT o.id, o.id_user, o.created, o.cost, o.address, id_order_status, " +
            "osi.name, os.explanation FROM shop_order o INNER JOIN order_status os ON o.id_order_status=os.id " +
            "LEFT JOIN order_status_detail osi ON os.id=osi.id " +
            "LEFT JOIN language l ON osi.id_language=l.id WHERE " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY o.created DESC";

    private static final String SELECT_ORDERS_BY_ID_USER_QUERY = "SELECT o.id, o.id_user, o.created, o.cost, o.address, id_order_status, " +
            "osi.name, os.explanation FROM shop_order o INNER JOIN order_status os ON o.id_order_status=os.id " +
            "LEFT JOIN order_status_detail osi ON os.id=osi.id " +
            "LEFT JOIN language l ON osi.id_language=l.id WHERE o.id_user=? AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY o.created DESC";

    private static final String SELECT_ORDER_BY_ID_QUERY = "SELECT o.id, o.id_user, o.created, o.cost, o.address, id_order_status, " +
            "osi.name, os.explanation FROM shop_order o INNER JOIN order_status os ON o.id_order_status=os.id " +
            "LEFT JOIN order_status_detail osi ON os.id=osi.id " +
            "LEFT JOIN language l ON osi.id_language=l.id WHERE o.id=?  AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?)";

    private static final String SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY = "SELECT oi.id, oi.id_order, oi.id_product, " +
            "p.name, p.description, " +
            "p.image_link, p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name as name_category, c.image, c.id_parent, ci.id_language, l.code, l.name as name_language, " +
            "pr.name as name_producer, " +
            "oi.count as count_order_item, retained_product_price FROM order_item oi INNER JOIN product p ON " +
            "oi.id_product=p.id inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 AND id_order=?";

    private static final String INSERT_ORDER_QUERY = "INSERT INTO shop_order (id_user, created, id_order_status, cost, address) VALUES (?,?,?,?,?)";
    private static final String UPDATE_ORDER_QUERY = "UPDATE shop_order SET id_user = ?, created = ?, id_order_status = ?, cost = ?, address = ? WHERE id = ?";
    private static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_item (id_order, id_product, count, retained_product_price) " +
            "VALUES (?,?,?,?)";

    private static final String UPDATE_PRODUCT_REDUCE_COUNT_QUERY = "UPDATE product SET count = count - ? WHERE id = ?";
    private static final String UPDATE_PRODUCT_INCREASE_COUNT_QUERY = "UPDATE product SET count = count + ? WHERE id = ?";

    public Optional<Order> getOrderById(long id, String language) {
        Order order = Optional.ofNullable(JdbcTemplate.select(SELECT_ORDER_BY_ID_QUERY, orderResultSetHandler, id, language))
                .orElseThrow(() -> new OrderNotFoundException("Cannot find order with id = " + id));
        List<OrderItem> orderItems = JdbcTemplate.select(SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY,
                orderItemsResultSetHandler, order.getId());
        order.setItems(orderItems);
        return Optional.of(order);
    }

    public List<Order> getAllOrders(String language) {
        List<Order> orders = JdbcTemplate.select(SELECT_ALL_ORDERS_QUERY,
                ordersResultSetHandler, language);
        orders = orders.stream()
                .distinct()
                .peek(order -> order.setItems(JdbcTemplate.select(SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY,
                        orderItemsResultSetHandler, order.getId())))
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public List<Order> getOrdersByIdUser(int idUser, String language) {
        List<Order> orders = JdbcTemplate.select(SELECT_ORDERS_BY_ID_USER_QUERY, ordersResultSetHandler, idUser,
                language);
        orders = orders.stream()
                .distinct()
                .peek(order -> order.setItems(JdbcTemplate.select(SELECT_ALL_ORDER_ITEMS_BY_ID_ORDER_QUERY,
                        orderItemsResultSetHandler, order.getId())))
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public void saveOrder(Order order) {
        doInTransaction(connection -> {
            long idOrder = JdbcTemplate.executeUpdateInTransactionWithGenKeys(connection, INSERT_ORDER_QUERY, idResultSetHandler,
                    order.getIdUser(), order.getCreated(), order.getStatus().getId(), order.getCost(), order.getAddress());
            order.getItems().stream()
                    .peek(el -> el.setIdOrder(idOrder))
                    .forEach(el -> {
                        JdbcTemplate.executeUpdateInTransactionWithGenKeys(connection, INSERT_ORDER_ITEM_QUERY, idResultSetHandler, el.getIdOrder(),
                                el.getProduct().getId(), el.getCount(), el.getRetainedProductPrice());
                        JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_PRODUCT_REDUCE_COUNT_QUERY,
                                el.getCount(), el.getProduct().getId());
                    });
        });
    }

    @Override
    public void updateOrderStatusInOrder(Order order, long id) {
        JdbcTemplate.executeUpdate(UPDATE_ORDER_QUERY, order.getIdUser(),
                order.getCreated(), order.getStatus().getId(), order.getCost(), order.getAddress(), id);
    }

    @Override
    public void updateOrderStatusAndReturnProducts(Order order, long id) {
        doInTransaction(connection -> {
            JdbcTemplate.executeUpdate(UPDATE_ORDER_QUERY, order.getIdUser(),
                    order.getCreated(), order.getStatus().getId(), order.getCost(), order.getAddress(), id);
            order.getItems().stream()
                    .forEach(el -> JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_PRODUCT_INCREASE_COUNT_QUERY,
                            el.getCount(), el.getProduct().getId()));
        });
    }
}
