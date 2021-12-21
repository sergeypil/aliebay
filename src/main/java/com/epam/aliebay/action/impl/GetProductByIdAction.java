package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.ProductNotFoundException;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.PRODUCTS_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.PRODUCT_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.PRODUCT_DETAIL_JSP;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetProductByIdAction implements Action {
    private final ProductDao productDao = PostgreSqlDaoFactory.getInstance().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idProduct = Integer.parseInt(req.getParameter(ID_PARAMETER));
        List<Product> products = productDao.getAllProducts();
        req.setAttribute(PRODUCTS_ATTRIBUTE, products);
        Product product = productDao.getProductById(idProduct).orElseThrow(
                () -> new ProductNotFoundException("Cannot find product with id = " + idProduct));
        req.setAttribute(PRODUCT_ATTRIBUTE, product);
        RoutingUtils.forwardToPage(PRODUCT_DETAIL_JSP, req, resp);
    }
}
