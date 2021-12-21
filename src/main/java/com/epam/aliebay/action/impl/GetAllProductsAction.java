package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.PRODUCTS_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class GetAllProductsAction implements Action {
    private final ProductDao productDao = PostgreSqlDaoFactory.getInstance().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String numberOfPageParam = req.getParameter(NUMBER_OF_PAGE_PARAMETER);
        String sortParam = req.getParameter(SORT_PARAMETER);
        String searchParam = req.getParameter(SEARCH_PARAMETER);
        String idCategoryParam = req.getParameter(ID_CATEGORY_PARAMETER);
        String idProducerParam = req.getParameter(ID_PRODUCER_PARAMETER);
        int numberOfPage;
        if (ValidationUtils.isParameterNullOrEmpty(numberOfPageParam)) {
            numberOfPage = 1;//If user didn't specify number of page, show first page
        } else {
            numberOfPage = Integer.parseInt(numberOfPageParam);
        }
        int offset = (numberOfPage - 1) * COUNT_PRODUCTS_ON_PRODUCT_PAGE;
        List<Product> products;
        int totalCountOfProducts;
        if (!ValidationUtils.isParameterNullOrEmpty(searchParam)) {
            if (!ValidationUtils.isParameterNullOrEmpty(sortParam) && sortParam.equals(PRICE_SORT_PARAMETER)) {
                products = productDao.getProductsWithOffsetLimitOrderSearch(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, sortParam, searchParam);
                req.setAttribute(SEARCH_PARAMETER_ATTRIBUTE, searchParam);
                req.setAttribute(SORT_PARAM_ATTRIBUTE, sortParam);
                totalCountOfProducts = productDao.getCountOfProductsWithSearch(searchParam);
            } else {
                products = productDao.getProductsWithOffsetLimitOrderSearch(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, ID_SORT_PARAMETER, searchParam);
                req.setAttribute(SEARCH_PARAMETER_ATTRIBUTE, searchParam);
                totalCountOfProducts = productDao.getCountOfProductsWithSearch(searchParam);
            }
        } else if (!ValidationUtils.isParameterNullOrEmpty(idCategoryParam)) {
            int idCategory = Integer.parseInt(idCategoryParam);
            if (!ValidationUtils.isParameterNullOrEmpty(sortParam) && sortParam.equals(PRICE_SORT_PARAMETER)) {
                products = productDao.getProductsByCategoryWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, sortParam, idCategory);
                req.setAttribute(ID_CATEGORY_PARAMETER_ATTRIBUTE, idCategoryParam);
                req.setAttribute(SORT_PARAM_ATTRIBUTE, sortParam);
                totalCountOfProducts = productDao.getCountOfProductsByCategory(idCategory);
            } else {
                products = productDao.getProductsByCategoryWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, ID_SORT_PARAMETER, idCategory);
                req.setAttribute(ID_CATEGORY_PARAMETER_ATTRIBUTE, idCategoryParam);
                totalCountOfProducts = productDao.getCountOfProductsByCategory(idCategory);
            }
        } else if (!ValidationUtils.isParameterNullOrEmpty(idProducerParam)) {
            int idProducer = Integer.parseInt(idProducerParam);
            if (!ValidationUtils.isParameterNullOrEmpty(sortParam) && sortParam.equals(PRICE_SORT_PARAMETER)) {
                products = productDao.getProductsByProducerWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, sortParam, idProducer);
                req.setAttribute(ID_PRODUCER_PARAMETER_ATTRIBUTE, idProducerParam);
                req.setAttribute(SORT_PARAM_ATTRIBUTE, sortParam);
                totalCountOfProducts = productDao.getCountOfProductsByProducer(idProducer);
            } else {
                products = productDao.getProductsByProducerWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, ID_SORT_PARAMETER, idProducer);
                req.setAttribute(ID_PRODUCER_PARAMETER_ATTRIBUTE, idProducerParam);
                totalCountOfProducts = productDao.getCountOfProductsByProducer(idProducer);
            }
        } else {
            if (!ValidationUtils.isParameterNullOrEmpty(sortParam) && sortParam.equals(PRICE_SORT_PARAMETER)) {
                products = productDao.getProductsWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, PRICE_SORT_PARAMETER);
                req.setAttribute(SORT_PARAM_ATTRIBUTE, sortParam);
                totalCountOfProducts = productDao.getCountOfProducts();
            } else {
                products = productDao.getProductsWithOffsetLimit(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE);
                totalCountOfProducts = productDao.getCountOfProducts();
            }
        }
        int countOfPages = (int) Math.ceil((double) totalCountOfProducts / COUNT_PRODUCTS_ON_PRODUCT_PAGE);
        req.setAttribute(COUNT_OF_PAGES_ATTRIBUTE, countOfPages);
        req.setAttribute(NUMBER_OF_PAGE_ATTRIBUTE, numberOfPage);
        req.setAttribute(PRODUCTS_ATTRIBUTE, products);
        RoutingUtils.forwardToPage(PRODUCTS_JSP, req, resp);
    }
}
