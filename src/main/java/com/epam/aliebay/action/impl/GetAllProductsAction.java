package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.dto.ProductsDto;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.AppUtils;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.validation.field.NotNullOrEmptyValidator;

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
    private final static int COUNT_OF_PAGES_POSSIBLE_TO_SELECT = 10;
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProductsDto productsDto = createProductsDtoFromRequest(req);
        int numberOfPage = getNumberOfPage(productsDto);
        int offset = (numberOfPage - 1) * COUNT_PRODUCTS_ON_PRODUCT_PAGE;
        List<Product> products;
        int totalCountOfProducts;
        if (NotNullOrEmptyValidator.isValid(productsDto.getSearchParam())) {
            products = getProductsBySearchRequest(req, productsDto, offset);
            totalCountOfProducts = productDao.getCountOfProductsWithSearch(productsDto.getSearchParam());
            req.setAttribute(SEARCH_PARAMETER_ATTRIBUTE, productsDto.getSearchParam());
        } else if (NotNullOrEmptyValidator.isValid(productsDto.getIdCategoryParam())) {
            products = getProductsByCategoryRequest(req, productsDto, offset);
            totalCountOfProducts = productDao.getCountOfProductsByCategory(Integer.parseInt(productsDto.getIdCategoryParam()));
            req.setAttribute(ID_CATEGORY_PARAMETER_ATTRIBUTE, productsDto.getIdCategoryParam());
            List<Producer> producers = ActionUtils.getProducersByProducts(products);
            AppUtils.setProducersToAttributeAsidePanel(req, producers);
        } else if (NotNullOrEmptyValidator.isValid(productsDto.getIdProducerParam())) {
            products = getProductsByProducerRequest(req, productsDto, offset);
            totalCountOfProducts = productDao.getCountOfProductsByProducer(Integer.parseInt(productsDto.getIdProducerParam()));
            req.setAttribute(ID_PRODUCER_PARAMETER_ATTRIBUTE, productsDto.getIdProducerParam());
        } else {
            products = getProductsByDefault(req, productsDto, offset);
            totalCountOfProducts = productDao.getCountOfProducts();
        }
        int countOfPages = (int) Math.ceil((double) totalCountOfProducts / COUNT_PRODUCTS_ON_PRODUCT_PAGE);
        int startPage = calculateStartPage(numberOfPage, countOfPages);
        int endPage = calculateEndPage(startPage, countOfPages);

        setAttributesForProductsPage(req, numberOfPage, products, countOfPages, startPage, endPage);
        RoutingUtils.forwardToPage(PRODUCTS_JSP, req, resp);
    }

    private List<Product> getProductsBySearchRequest(HttpServletRequest req, ProductsDto productsDto, int offset) {
        List<Product> products;
        if (NotNullOrEmptyValidator.isValid(productsDto.getSortParam()) && productsDto.getSortParam().equals(PRICE_SORT_PARAMETER)) {
            products = productDao.getProductsWithOffsetLimitOrderSearch(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE,
                    productsDto.getSortParam(), productsDto.getSearchParam());
            req.setAttribute(SORT_PARAM_ATTRIBUTE, productsDto.getSortParam());

        } else {
            products = productDao.getProductsWithOffsetLimitOrderSearch(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, ID_SORT_PARAMETER,
                    productsDto.getSearchParam());
        }
        return products;
    }

    private List<Product> getProductsByCategoryRequest(HttpServletRequest req, ProductsDto productsDto, int offset) {
        List<Product> products;
        int idCategory = Integer.parseInt(productsDto.getIdCategoryParam());
        if (NotNullOrEmptyValidator.isValid(productsDto.getSortParam()) && productsDto.getSortParam().equals(PRICE_SORT_PARAMETER)) {
            products = productDao.getProductsByCategoryWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE,
                    productsDto.getSortParam(), idCategory);
            req.setAttribute(SORT_PARAM_ATTRIBUTE, productsDto.getSortParam());
        } else {
            products = productDao.getProductsByCategoryWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE,
                    ID_SORT_PARAMETER, idCategory);
        }
        return products;
    }

    private List<Product> getProductsByProducerRequest(HttpServletRequest req, ProductsDto productsDto, int offset) {
        List<Product> products;
        int idProducer = Integer.parseInt(productsDto.getIdProducerParam());
        if (NotNullOrEmptyValidator.isValid(productsDto.getSortParam()) && productsDto.getSortParam().equals(PRICE_SORT_PARAMETER)) {
            products = productDao.getProductsByProducerWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE,
                    productsDto.getSortParam(), idProducer);
            req.setAttribute(SORT_PARAM_ATTRIBUTE, productsDto.getSortParam());
        } else {
            products = productDao.getProductsByProducerWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE,
                    ID_SORT_PARAMETER, idProducer);
        }
        return products;
    }

    private List<Product> getProductsByDefault(HttpServletRequest req, ProductsDto productsDto, int offset) {
        List<Product> products;
        if (NotNullOrEmptyValidator.isValid(productsDto.getSortParam()) && productsDto.getSortParam().equals(PRICE_SORT_PARAMETER)) {
            products = productDao.getProductsWithOffsetLimitOrder(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE, PRICE_SORT_PARAMETER);
            req.setAttribute(SORT_PARAM_ATTRIBUTE, productsDto.getSortParam());
        } else {
            products = productDao.getProductsWithOffsetLimit(offset, COUNT_PRODUCTS_ON_PRODUCT_PAGE);
        }
        return products;
    }

    private int getNumberOfPage(ProductsDto productsDto) {
        int numberOfPage;
        if (NotNullOrEmptyValidator.isValid(productsDto.getNumberOfPageParam())) {
            numberOfPage = Integer.parseInt(productsDto.getNumberOfPageParam());
        } else {
            numberOfPage = 1;//If user didn't specify number of page, show first page
        }
        return numberOfPage;
    }

    private ProductsDto createProductsDtoFromRequest(HttpServletRequest req) {
        ProductsDto productsDto = new ProductsDto();
        productsDto.setNumberOfPageParam(req.getParameter(NUMBER_OF_PAGE_PARAMETER));
        productsDto.setSortParam(req.getParameter(SORT_PARAMETER));
        productsDto.setSearchParam(req.getParameter(SEARCH_PARAMETER));
        productsDto.setIdCategoryParam(req.getParameter(ID_CATEGORY_PARAMETER));
        productsDto.setIdProducerParam(req.getParameter(ID_PRODUCER_PARAMETER));
        return productsDto;
    }

    private void setAttributesForProductsPage(HttpServletRequest req, int numberOfPage, List<Product> products,
                                              int countOfPages, int startPage, int endPage) {
        req.setAttribute(COUNT_OF_PAGES_ATTRIBUTE, countOfPages);
        req.setAttribute(NUMBER_OF_PAGE_ATTRIBUTE, numberOfPage);
        req.setAttribute(PRODUCTS_ATTRIBUTE, products);
        req.setAttribute(START_PAGE_ATTRIBUTE, startPage);
        req.setAttribute(END_PAGE_ATTRIBUTE, endPage);
    }

    private int calculateEndPage(int startPage, int countOfPages) {
        if (countOfPages < startPage + COUNT_OF_PAGES_POSSIBLE_TO_SELECT - 1) {
            return countOfPages;
        }
        return startPage + COUNT_OF_PAGES_POSSIBLE_TO_SELECT - 1;
    }

    private int calculateStartPage(int numberOfPage, int countOfPages) {
        if (numberOfPage <= COUNT_OF_PAGES_POSSIBLE_TO_SELECT / 2 || countOfPages <= COUNT_OF_PAGES_POSSIBLE_TO_SELECT) {
            return 1;//Start page = 1 while number of selected page less than half of possible
        } else if (countOfPages - COUNT_OF_PAGES_POSSIBLE_TO_SELECT / 2 <= numberOfPage) {
            return countOfPages - COUNT_OF_PAGES_POSSIBLE_TO_SELECT + 1;
        } else {
            return numberOfPage - COUNT_OF_PAGES_POSSIBLE_TO_SELECT / 2 - 1;
        }
    }
}
