package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.entity.*;
import com.epam.aliebay.exception.OrderStatusNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.model.ShoppingCart;
import com.epam.aliebay.util.SessionUtils;
import com.epam.aliebay.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.epam.aliebay.constant.ActionConstants.GET_ACCOUNT_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.ID_ORDER_STATUS_IN_PROCESS;
import static com.epam.aliebay.constant.JspNameConstants.CHECKOUT_JSP;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class MakeOrderAction implements Action {
    private final OrderDao orderDao = PostgreSqlDaoFactory.getInstance().getOrderDao();
    private final OrderStatusDao orderStatusDao = PostgreSqlDaoFactory.getInstance().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean areAllParametersValid = true;
        if (!ValidationUtils.isValidCardNumber(req.getParameter(CARD_NUMBER_PARAMETER))) {
            req.setAttribute(WRONG_CARD_NUMBER_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidSecurityCode(req.getParameter(SECURITY_CODE_PARAMETER))) {
            req.setAttribute(WRONG_SECURITY_CODE_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidCardHolderName(req.getParameter(CARD_HOLDER_PARAMETER))) {
            req.setAttribute(WRONG_CARD_HOLDER_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidDate(req.getParameter(EXPIRATION_DATE_PARAMETER))) {
            req.setAttribute(WRONG_DATE_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidAddress(req.getParameter(ADDRESS_PARAMETER))) {
            req.setAttribute(WRONG_ADDRESS_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!areAllParametersValid) {
            RoutingUtils.forwardToPage(CHECKOUT_JSP, req, resp);
        } else {
            User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
            ShoppingCart cart = (ShoppingCart) req.getSession().getAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE);
            OrderStatus orderStatus = orderStatusDao.getOrderStatusById(ID_ORDER_STATUS_IN_PROCESS,
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                    () -> new OrderStatusNotFoundException("Cannot find order status with id = " +
                            ID_ORDER_STATUS_IN_PROCESS));
            Order order = new Order();
            order.setIdUser(user.getId());
            order.setCreated(Timestamp.from(Instant.now()));
            order.setStatus(orderStatus);
            List<OrderItem> orderItems = new ArrayList<>();
            cart.getProducts().entrySet().forEach(el -> {
                        OrderItem orderItem = new OrderItem();
                        Product product = el.getKey();
                        orderItem.setProduct(product);
                        Integer count = el.getValue().getCount();
                        orderItem.setCount(count);
                        orderItem.setRetainedProductPrice(product.getPrice());
                        orderItems.add(orderItem);
                    });
            order.setItems(orderItems);
            order.setCost(cart.getTotalCost());
            order.setAddress(req.getParameter(ADDRESS_PARAMETER));
            orderDao.saveOrder(order);
            SessionUtils.clearCurrentShoppingCart(req, resp);
            req.getSession().removeAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE);
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ACCOUNT_PAGE_ACTION);
        }
    }
}

