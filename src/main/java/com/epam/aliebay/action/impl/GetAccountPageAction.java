package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderStatus;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.ID_ORDER_STATUS_CANCELLED;
import static com.epam.aliebay.constant.OtherConstants.ID_ORDER_STATUS_IN_PROCESS;

public class GetAccountPageAction implements Action {
    private final OrderDao orderDao = PostgreSqlDaoFactory.getInstance().getOrderDao();
    private final OrderStatusDao orderStatusDao = PostgreSqlDaoFactory.getInstance().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
        List<Order> currentUserOrders = orderDao.getOrdersByIdUser(user.getId(), (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        req.getSession().setAttribute(CURRENT_USER_ORDERS_ATTRIBUTE, currentUserOrders);

        List<OrderStatus> orderStatuses = orderStatusDao.getAllOrderStatuses((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        List<OrderStatus> orderStatusesForCustomer = orderStatuses.stream()
                .filter(el -> el.getId() == ID_ORDER_STATUS_IN_PROCESS || el.getId() == ID_ORDER_STATUS_CANCELLED)
                .collect(Collectors.toList());
        req.getSession().setAttribute(ORDER_STATUSES_FOR_CUSTOMER_ATTRIBUTE, orderStatusesForCustomer);
        RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
    }
}
