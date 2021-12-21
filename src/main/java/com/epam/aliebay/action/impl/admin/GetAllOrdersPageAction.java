package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderStatus;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_ORDERS_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.ERROR_403_MESSAGE;
import static com.epam.aliebay.constant.OtherConstants.ERROR_403_TITLE;

public class GetAllOrdersPageAction implements Action {
    private final OrderDao orderDao = PostgreSqlDaoFactory.getInstance().getOrderDao();
    private final OrderStatusDao orderStatusDao = PostgreSqlDaoFactory.getInstance().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (AccessValidator.isAccessPermitted(req)) {
            List<Order> orders = orderDao.getAllOrders((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            List<OrderStatus> orderStatuses = orderStatusDao.getAllOrderStatuses((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            req.setAttribute(ALL_ORDERS_ATTRIBUTE, orders);
            req.setAttribute(ORDER_STATUSES_ATTRIBUTE, orderStatuses);
            RoutingUtils.forwardToPage(ADMIN_ORDERS_JSP, req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_403_TITLE);
            req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_403_MESSAGE);
            RoutingUtils.forwardToPage(ERROR_JSP, req, resp);
        }
    }
}
