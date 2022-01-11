package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public class OrderStatusDaoImpl extends AbstractBaseDao implements OrderStatusDao {
    private static final ResultSetHandler<OrderStatus> orderStatusResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.ORDER_STATUS_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<OrderStatus>> orderStatusesResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_STATUS_RESULT_SET_HANDLER);

    private static final String SELECT_ORDER_STATUS_BY_ID_QUERY = "SELECT os.id AS order_status_id, osd.name, os.explanation FROM order_status os " +
            "LEFT JOIN order_status_detail osd ON os.id = osd.id " +
            "LEFT JOIN language l ON osd.language_id = l.id WHERE os.id = ? " +
            "AND l.id in (SELECT l.id FROM language l WHERE l.code = ?)";
    private static final String SELECT_ALL_ORDER_STATUSES_QUERY = "SELECT os.id AS order_status_id, osd.name, os.explanation FROM order_status os " +
            "LEFT JOIN order_status_detail osd ON os.id = osd.id " +
            "LEFT JOIN language l ON osd.language_id = l.id WHERE " +
            "l.id in (SELECT l.id FROM language l WHERE l.code = ?)";

    @Override
    public Optional<OrderStatus> getOrderStatusById(int id, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_ORDER_STATUS_BY_ID_QUERY, orderStatusResultSetHandler, id, language));
    }

    @Override
    public List<OrderStatus> getAllOrderStatuses(String language) {
        return JdbcTemplate.select(SELECT_ALL_ORDER_STATUSES_QUERY, orderStatusesResultSetHandler, language);
    }

}
