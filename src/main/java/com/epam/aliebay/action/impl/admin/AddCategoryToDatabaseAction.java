package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Language;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_CATEGORIES_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_CATEGORY_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class AddCategoryToDatabaseAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddCategoryToDatabaseAction.class);
    private final CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else if (AccessValidator.isAccessPermitted(req)) {
            List<Language> languages = (List<Language>) req.getSession().getAttribute(APP_LANGUAGES_ATTRIBUTE);
            Map<Integer, Category> langToCategory;
            Map<Integer, Boolean> langToWrongName = new HashMap<>();
            if (req.getSession().getAttribute(LANG_TO_CATEGORY_ATTRIBUTE) == null) {
                langToCategory = new HashMap<>();
                languages.forEach(lang -> {
                    Category category = new Category();
                    langToCategory.put(lang.getId(), category);
                });
            } else {
                langToCategory = (Map<Integer, Category>) req.getSession().getAttribute(LANG_TO_CATEGORY_ATTRIBUTE);
            }
            boolean areAllParametersValid = ActionUtils.validateRequestFromCategoryForm(req, langToCategory, langToWrongName);
            if (!areAllParametersValid) {
                req.getSession().setAttribute(LANG_TO_WRONG_NAME_ATTRIBUTE, langToWrongName);
                req.getSession().setAttribute(LANG_TO_CATEGORY_ATTRIBUTE, langToCategory);
                req.setAttribute(ACTION_ATTRIBUTE, ADD_CATEGORY_FORM_ACTION);
                RoutingUtils.forwardToPage(ADD_CHANGE_CATEGORY_JSP, req, resp);
            } else {
                categoryDao.saveCategory(langToCategory);
                LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                        + " added category " + langToCategory.get(1).getName() + " to database");
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_CATEGORIES_PAGE_ACTION);
            }
        }
        else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
