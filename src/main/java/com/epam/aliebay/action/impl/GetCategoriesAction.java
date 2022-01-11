package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.ActionConstants.GET_PRODUCTS_PAGE_ACTION;
import static com.epam.aliebay.constant.JspNameConstants.CATEGORIES_JSP;
import static com.epam.aliebay.constant.OtherConstants.ID_PARENT_OF_HIGHEST_HIERARCHY_CATEGORY;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_CATEGORY_PARAMETER;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetCategoriesAction implements Action {
    private final CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idParentCategory;
        String idParentCategoryParam = req.getParameter(ID_PARAMETER);
        if (idParentCategoryParam == null) {
            idParentCategory = ID_PARENT_OF_HIGHEST_HIERARCHY_CATEGORY;
        }
        else {
            idParentCategory = Integer.parseInt(idParentCategoryParam);
        }
        List<Category> categories = categoryDao.getAllCategoriesByParentId(idParentCategory,
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        if (categories.isEmpty()) {
            resp.sendRedirect(req.getAttribute(AttributeConstants.HOST_NAME_ATTRIBUTE) +
                    GET_PRODUCTS_PAGE_ACTION + "?" + ID_CATEGORY_PARAMETER + "=" + idParentCategoryParam);
        }
        else {
            req.setAttribute(AttributeConstants.CATEGORIES_FOR_CATEGORIES_PAGE_ATTRIBUTE, categories);
            RoutingUtils.forwardToPage(CATEGORIES_JSP, req, resp);
        }
    }
}
