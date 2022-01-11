package com.epam.aliebay.action.impl.ajax;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderStatus;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.exception.OrderNotFoundException;
import com.epam.aliebay.exception.OrderStatusNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_ORDER_PARAMETER;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_ORDER_STATUS_PARAMETER;

public class ChangeOrderStatusAction implements Action {
    private final OrderDao orderDao = DaoFactory.getDaoFactory().getOrderDao();
    private final OrderStatusDao orderStatusDao = DaoFactory.getDaoFactory().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) != null) {
            String idOrderParam = req.getParameter(ID_ORDER_PARAMETER);
            String idOrderStatusParam = req.getParameter(ID_ORDER_STATUS_PARAMETER);
            long idOrder = Long.parseLong(idOrderParam);
            int desiredIdOrderStatus = Integer.parseInt(idOrderStatusParam);
            Order order = orderDao.getOrderById(idOrder, (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                    () -> new OrderNotFoundException("Cannot find order with id = " + idOrder));
            if (((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getRole().equalsIgnoreCase(ADMIN_ROLE) ||
                    ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getId() == order.getUserId()) {
                setDesiredOrderStatus(req, desiredIdOrderStatus, order);
                sendResponse(order.getStatus(), req, resp);
            }
            else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void setDesiredOrderStatus(HttpServletRequest req, int desiredIdOrderStatus, Order order) {
        if (order.getStatus().getId() != desiredIdOrderStatus && order.getStatus().getId() != ID_ORDER_STATUS_CANCELLED) {
            OrderStatus orderStatus = orderStatusDao.getOrderStatusById(desiredIdOrderStatus,
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                    () -> new OrderStatusNotFoundException("Cannot order status with id = " + desiredIdOrderStatus));
            order.setStatus(orderStatus);
            updateOrderStatusInDb(desiredIdOrderStatus, order);
        }
    }

    private void updateOrderStatusInDb(int desiredIdOrderStatus, Order order) {
        if (desiredIdOrderStatus == ID_ORDER_STATUS_CANCELLED) {
            orderDao.updateOrderStatusAndRecalculateCountOfProducts(order, order.getId());
        } else {
            orderDao.updateOrderStatusInOrder(order, order.getId());
        }
    }

    private void sendResponse(OrderStatus orderStatus, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject data = new JSONObject();
        data.put(ORDER_STATUS_JSON, orderStatus.getName());
        data.put(ID_ORDER_STATUS_JSON, orderStatus.getId());
        RoutingUtils.sendJSON(data, req, resp);
    }
}
