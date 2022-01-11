package com.epam.aliebay.filter;

import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.util.AppUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoadCategoriesAndProducersFilter extends AbstractFilter {
    private final CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();
    private final ProducerDao producerDao = DaoFactory.getDaoFactory().getProducerDao();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        List<Category> categories = categoryDao.getLeafCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        AppUtils.setCategoriesToAttributeRightAsidePanel(req, categories);

        List<Producer> producers = producerDao.getAllProducers();
        AppUtils.setProducersToAttributeAsidePanel(req, producers);
        chain.doFilter(req, resp);
    }
}
