package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.COUNT_CATEGORIES_LEFT_PANEL_ON_HOME_PAGE;
import static com.epam.aliebay.constant.JspNameConstants.HOME_JSP;

public class GetHomePageAction implements Action {

    private final CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Category> categories = categoryDao.getAllCategoriesByParentId(0, (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        if (categories.size() >= COUNT_CATEGORIES_LEFT_PANEL_ON_HOME_PAGE) {
            req.setAttribute(CATEGORIES_ON_LEFT_PANEL_ATTRIBUTE, categories.subList(0, COUNT_CATEGORIES_LEFT_PANEL_ON_HOME_PAGE));
        }
        else {
            req.setAttribute(CATEGORIES_ON_LEFT_PANEL_ATTRIBUTE, categories);
        }
        RoutingUtils.forwardToPage(HOME_JSP, req, resp);
    }
}
