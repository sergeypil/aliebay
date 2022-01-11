package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.ProductNotFoundException;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.AppUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.PRODUCT_DETAIL_JSP;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetProductByIdAction implements Action {
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idProduct = Integer.parseInt(req.getParameter(ID_PARAMETER));
        Product product = productDao.getProductById(idProduct).orElseThrow(
                () -> new ProductNotFoundException("Cannot find product with id = " + idProduct));
        req.setAttribute(PRODUCT_ATTRIBUTE, product);
        List<Product> products = productDao.getProductsByCategory(product.getCategory().getId());
        req.setAttribute(PRODUCTS_ATTRIBUTE, products);
        List<Producer> producers = ActionUtils.getProducersByProducts(products);
        AppUtils.setProducersToAttributeAsidePanel(req, producers);
        RoutingUtils.forwardToPage(PRODUCT_DETAIL_JSP, req, resp);
    }
}
