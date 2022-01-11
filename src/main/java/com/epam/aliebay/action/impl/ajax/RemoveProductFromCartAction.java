package com.epam.aliebay.action.impl.ajax;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.ProductNotFoundException;
import com.epam.aliebay.model.ShoppingCart;
import com.epam.aliebay.model.ShoppingCartItem;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.util.SessionUtils;
import com.epam.aliebay.util.WebUtils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PRODUCT_PARAMETER;

public class RemoveProductFromCartAction implements Action {

    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long idProduct = Long.parseLong(req.getParameter(ID_PRODUCT_PARAMETER));
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);

        if (shoppingCart != null) {
            Map<Product, ShoppingCartItem> productsInShoppingCart = shoppingCart.getProducts();
            Product product = productDao.getProductById(idProduct).orElseThrow(
                    () -> new ProductNotFoundException("Cannot find product with id = " + idProduct));
            if (productsInShoppingCart.containsKey(product)) {
                productsInShoppingCart.remove(product);
                shoppingCart.refreshStatistics();
                String cookieCart = ActionUtils.serializeShoppingCart(productsInShoppingCart);
                SessionUtils.setCurrentShoppingCart(req, shoppingCart);
                WebUtils.setCookie(SHOPPING_CART_COOCKIE, cookieCart, SHOPPING_CART_COOCKIE_AGE, resp);
                sendResponse(shoppingCart, req, resp);
            }
        }
    }

    private void sendResponse(ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject cartInfo = new JSONObject();
        cartInfo.put(TOTAL_COUNT_JSON, shoppingCart.getTotalCount());
        cartInfo.put(TOTAL_COST_JSON, shoppingCart.getTotalCost().toString());
        RoutingUtils.sendJSON(cartInfo, req, resp);
    }
}
