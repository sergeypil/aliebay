package com.epam.aliebay.action.impl.ajax;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderStatus;
import com.epam.aliebay.exception.OrderStatusNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_ORDER_PARAMETER;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_ORDER_STATUS_PARAMETER;

public class ChangeOrderStatusAction implements Action {
    private final OrderDao orderDao = PostgreSqlDaoFactory.getInstance().getOrderDao();
    private final OrderStatusDao orderStatusDao = PostgreSqlDaoFactory.getInstance().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idOrderParam = req.getParameter(ID_ORDER_PARAMETER);
        String idOrderStatusParam = req.getParameter(ID_ORDER_STATUS_PARAMETER);
        long idOrder = Long.parseLong(idOrderParam);
        int desiredIdOrderStatus = Integer.parseInt(idOrderStatusParam);
        Order order = orderDao.getOrderById(idOrder, (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                () -> new OrderStatusNotFoundException("Order with id " + idOrder + "not found"));
        if (order.getStatus().getId() != desiredIdOrderStatus && order.getStatus().getId() != ID_ORDER_STATUS_CANCELLED) {
            OrderStatus orderStatus = orderStatusDao.getOrderStatusById(desiredIdOrderStatus,
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                    () -> new OrderStatusNotFoundException("Order status with id " + desiredIdOrderStatus + "not found"));
            order.setStatus(orderStatus);
            if (desiredIdOrderStatus == ID_ORDER_STATUS_CANCELLED) {
                orderDao.updateOrderStatusAndReturnProducts(order, order.getId());
            }
            else {
                orderDao.updateOrderStatusInOrder(order, order.getId());
            }
        }
        sendResponse(order.getStatus(), req, resp);
    }
    private void sendResponse(OrderStatus orderStatus, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject data = new JSONObject();
        data.put(ORDER_STATUS_JSON, orderStatus.getName());
        data.put(ID_ORDER_STATUS_JSON, orderStatus.getId());
        RoutingUtils.sendJSON(data, req, resp);
    }
}
