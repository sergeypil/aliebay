package com.epam.aliebay.filter;

import com.epam.aliebay.dao.impl.ProductDaoImpl;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.model.ShoppingCart;
import com.epam.aliebay.model.ShoppingCartItem;
import com.epam.aliebay.util.SessionUtils;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.aliebay.constant.AttributeConstants.SHOPPING_CART_DESERIALIZATION_DONE;

public class AutoRestoreShoppingCartFilter extends AbstractFilter {
    private static final Logger LOGGER = Logger.getLogger(AutoRestoreShoppingCartFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if(req.getSession().getAttribute(SHOPPING_CART_DESERIALIZATION_DONE) == null){
            if(!SessionUtils.isCurrentShoppingCartCreated(req)) {
                Cookie cookie = SessionUtils.findShoppingCartCookie(req);
                if(cookie != null) {
                    ShoppingCart shoppingCart = deserializeShoppingCart(cookie.getValue());
                    if(shoppingCart != null) {
                        SessionUtils.setCurrentShoppingCart(req, shoppingCart);
                    }
                }
            }
            req.getSession().setAttribute(SHOPPING_CART_DESERIALIZATION_DONE, true);
        }
        chain.doFilter(req, resp);
    }

    private ShoppingCart deserializeShoppingCart(String value) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = value.split("\\|");
        for (String item : items) {
            try {
            String data[] = item.split("-");
            int idProduct = Integer.parseInt(data[0]);
            int count = Integer.parseInt(data[1]);
            ProductDaoImpl productDao = new ProductDaoImpl();
            Product product = productDao.getProductById(idProduct).get();
            shoppingCart.getProducts().put(product, new ShoppingCartItem(count, product.getPrice().multiply(BigDecimal.valueOf(count))));
            } catch (RuntimeException e) {
                LOGGER.error("Cannot add product to ShoppingCart during deserialization: item=" + item, e);
            }
        }
        shoppingCart.refreshStatistics();
        return shoppingCart;
    }
}
