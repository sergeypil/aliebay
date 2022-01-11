package com.epam.aliebay.action.impl.admin;

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
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_CATEGORIES_JSP;

public class GetAllCategoriesAsAdminAction implements Action {
    private final CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Category> categoriesAllLanguages = categoryDao.getAllCategoriesAllLanguages();
        Map<Integer, List<Category>> idToCategories = categoriesAllLanguages.stream()
                .collect(Collectors.groupingBy(Category::getId));

        req.setAttribute(ID_TO_CATEGORIES_ATTRIBUTE, idToCategories);
        req.setAttribute(CATEGORIES_ALL_LANGUAGES_ATTRIBUTE, categoriesAllLanguages);
        RoutingUtils.forwardToPage(ADMIN_CATEGORIES_JSP, req, resp);
    }
}
