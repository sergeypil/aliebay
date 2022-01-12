package com.epam.aliebay.util;

import com.epam.aliebay.model.ShoppingCart;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_SHOPPING_CART_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.SHOPPING_CART_COOKIE;

public final class SessionUtils {
    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        return (ShoppingCart) req.getSession().getAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE);
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
        return req.getSession().getAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
        req.getSession().setAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE, shoppingCart);
    }

    public static Cookie findShoppingCartCookie(HttpServletRequest req) {
        return WebUtils.findCookie(req, SHOPPING_CART_COOKIE);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE);
        WebUtils.setCookie(SHOPPING_CART_COOKIE, null, 0, resp);
    }
}
