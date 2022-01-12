package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.*;
import com.epam.aliebay.exception.OrderStatusNotFoundException;
import com.epam.aliebay.dto.CheckoutDto;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.model.ShoppingCart;
import com.epam.aliebay.util.SessionUtils;
import com.epam.aliebay.validation.form.CheckoutConstraintValidatorsStorage;
import com.epam.aliebay.validation.form.FormValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.aliebay.constant.ActionConstants.GET_ACCOUNT_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.CHECKOUT_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class MakeOrderAction implements Action {
    private final OrderDao orderDao = DaoFactory.getDaoFactory().getOrderDao();
    private final OrderStatusDao orderStatusDao = DaoFactory.getDaoFactory().getOrderStatusDao();
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (ActionUtils.isCountOfProductsInCartMatchesCountInDb(req, productDao)) {
            CheckoutDto checkoutDto = createCheckoutDtoFromRequest(req);
            Set<String> validationErrorAttributes = getValidationErrorAttributes(checkoutDto);
            if (validationErrorAttributes.size() != 0) {
                validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
                req.getSession().setAttribute(CHECKOUT_DTO, checkoutDto);
                RoutingUtils.forwardToPage(CHECKOUT_JSP, req, resp);
            } else {
                User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
                ShoppingCart cart = (ShoppingCart) req.getSession().getAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE);
                OrderStatus orderStatus = orderStatusDao.getOrderStatusById(ID_ORDER_STATUS_IN_PROCESS,
                        (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                        () -> new OrderStatusNotFoundException("Cannot find order status with id = " +
                                ID_ORDER_STATUS_IN_PROCESS));
                Order order = createOrder(req, user, cart, orderStatus);
                orderDao.saveOrder(order);
                SessionUtils.clearCurrentShoppingCart(req, resp);
                req.getSession().removeAttribute(CHECKOUT_DTO);
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ACCOUNT_PAGE_ACTION);
            }
        } else {
            RoutingUtils.forwardToErrorPage(HttpServletResponse.SC_BAD_REQUEST, ERROR_400_TITLE_PRODUCT_NOT_ENOUGH,
                    ERROR_400_MESSAGE_PRODUCT_NOT_ENOUGH, req, resp);
        }
    }

    private Order createOrder(HttpServletRequest req, User user, ShoppingCart cart, OrderStatus orderStatus) {
        Order order = new Order();
        order.setUserId(user.getId());
        order.setCreated(Timestamp.from(Instant.now()));
        order.setStatus(orderStatus);
        List<OrderItem> orderItems = new ArrayList<>();
        cart.getProducts().forEach((product, shopItem) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            Integer count = shopItem.getCount();
            orderItem.setCount(count);
            orderItem.setRetainedProductPrice(product.getPrice());
            orderItems.add(orderItem);
        });
        order.setItems(orderItems);
        order.setCost(cart.getTotalCost());
        order.setAddress(req.getParameter(ADDRESS_PARAMETER));
        return order;
    }

    private Set<String> getValidationErrorAttributes(CheckoutDto checkoutDto) {
        Set<String> validationErrorAttributes = new HashSet<>();
        CheckoutConstraintValidatorsStorage checkoutConstraintValidatorsStorage =
                CheckoutConstraintValidatorsStorage.createDefault(checkoutDto, validationErrorAttributes);
        FormValidator.validate(checkoutConstraintValidatorsStorage);
        return validationErrorAttributes;
    }

    private CheckoutDto createCheckoutDtoFromRequest(HttpServletRequest req) {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setCardNumber(req.getParameter(CARD_NUMBER_PARAMETER));
        checkoutDto.setSecurityCode(req.getParameter(SECURITY_CODE_PARAMETER));
        checkoutDto.setCardHolder(req.getParameter(CARD_HOLDER_PARAMETER));
        checkoutDto.setExpirationDate(req.getParameter(EXPIRATION_DATE_PARAMETER));
        checkoutDto.setAddress(req.getParameter(ADDRESS_PARAMETER));
        return checkoutDto;
    }
}

