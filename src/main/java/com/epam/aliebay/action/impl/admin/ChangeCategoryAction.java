package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Category;
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
import java.util.Map;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_CATEGORIES_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_CATEGORY_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;


public class ChangeCategoryAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ChangeCategoryAction.class);
    private final CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else if (AccessValidator.isAccessPermitted(req)) {
            Map<Integer, Category> langToCategory = (Map<Integer, Category>) req.getSession().getAttribute(LANG_TO_CATEGORY_ATTRIBUTE);
            Map<Integer, Boolean> langToWrongName = new HashMap<>();
            boolean areAllParametersValid = ActionUtils.validateRequestFromCategoryForm(req, langToCategory, langToWrongName);

            if (!areAllParametersValid) {
                req.getSession().setAttribute(LANG_TO_WRONG_NAME_ATTRIBUTE, langToWrongName);
                req.getSession().setAttribute(LANG_TO_CATEGORY_ATTRIBUTE, langToCategory);
                req.setAttribute(ACTION_ATTRIBUTE, CHANGE_CATEGORY_FORM_ACTION);
                RoutingUtils.forwardToPage(ADD_CHANGE_CATEGORY_JSP, req, resp);
            } else {
                categoryDao.updateCategory(langToCategory, langToCategory.get(1).getId());
                LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                        + " changed category " + langToCategory.get(1).getName() + " in database");
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_CATEGORIES_PAGE_ACTION);
            }
        } else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
