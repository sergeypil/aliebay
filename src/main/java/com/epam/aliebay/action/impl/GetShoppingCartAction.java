package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.model.ShoppingCart;
import com.epam.aliebay.util.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.JspNameConstants.SHOPPING_CART_JSP;

public class GetShoppingCartAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);;
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            SessionUtils.setCurrentShoppingCart(req, shoppingCart);
        }
        req.setAttribute(AttributeConstants.CART_PRODUCTS_ATTRIBUTE, shoppingCart.getProducts());
        RoutingUtils.forwardToPage(SHOPPING_CART_JSP, req, resp);
    }
}
