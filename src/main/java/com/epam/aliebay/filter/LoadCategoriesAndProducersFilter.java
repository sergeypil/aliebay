package com.epam.aliebay.filter;

import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Producer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.COUNT_CATEGORIES_RIGHT_PANEL_ON_PRODUCT_PAGE;
import static com.epam.aliebay.constant.OtherConstants.COUNT_PRODUCERS_RIGHT_PANEL_ON_PRODUCT_PAGE;

import javax.servlet.*;

public class LoadCategoriesAndProducersFilter extends AbstractFilter {
    private final CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();
    private final ProducerDao producerDao = PostgreSqlDaoFactory.getInstance().getProducerDao();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        List<Category> categories = categoryDao.getLeafCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        if (categories.size() < COUNT_CATEGORIES_RIGHT_PANEL_ON_PRODUCT_PAGE) {
            req.setAttribute(CATEGORIES_ON_RIGHT_PANEL_ATTRIBUTE, categories);
        } else {
            req.setAttribute(CATEGORIES_ON_RIGHT_PANEL_ATTRIBUTE, categories.subList(0, COUNT_CATEGORIES_RIGHT_PANEL_ON_PRODUCT_PAGE));
        }

        List<Producer> producers = producerDao.getAllProducers();
        if (producers.size() < COUNT_PRODUCERS_RIGHT_PANEL_ON_PRODUCT_PAGE) {
            req.setAttribute(PRODUCERS_ON_RIGHT_PANEL_ATTRIBUTE, producers);
        } else {
            req.setAttribute(PRODUCERS_ON_RIGHT_PANEL_ATTRIBUTE, producers.subList(0, COUNT_PRODUCERS_RIGHT_PANEL_ON_PRODUCT_PAGE));
        }
        chain.doFilter(req, resp);
    }
}
