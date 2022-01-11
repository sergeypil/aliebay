package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_PRODUCTS_JSP;

public class GetAllProductsAsAdminAction implements Action {
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Product> products = productDao.getAllProducts((String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        req.setAttribute(ALL_PRODUCTS_ATTRIBUTE, products);
        RoutingUtils.forwardToPage(ADMIN_PRODUCTS_JSP, req, resp);
    }
}
