package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderStatus;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_ORDERS_JSP;

public class GetAllOrdersPageAction implements Action {
    private final OrderDao orderDao = DaoFactory.getDaoFactory().getOrderDao();
    private final OrderStatusDao orderStatusDao = DaoFactory.getDaoFactory().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Order> orders = orderDao.getAllOrders((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        List<OrderStatus> orderStatuses = orderStatusDao.getAllOrderStatuses((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        req.setAttribute(ALL_ORDERS_ATTRIBUTE, orders);
        req.setAttribute(ORDER_STATUSES_ATTRIBUTE, orderStatuses);
        RoutingUtils.forwardToPage(ADMIN_ORDERS_JSP, req, resp);
    }
}
