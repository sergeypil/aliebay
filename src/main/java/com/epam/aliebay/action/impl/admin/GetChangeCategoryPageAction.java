package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Language;
import com.epam.aliebay.exception.CategoryNotFoundException;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_CATEGORY_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetChangeCategoryPageAction implements Action {
    private final CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else if (AccessValidator.isAccessPermitted(req)) {
            List<Language> languages = (List<Language>) req.getSession().getAttribute(APP_LANGUAGES_ATTRIBUTE);
            Map<Integer, Category> langToCategory = new HashMap<>();
            int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
            List<Category> editedCategories = categoryDao.getCategoryByIdAllLanguages(id);
            languages.forEach(lang -> {
                Category category = editedCategories.stream()
                        .filter(cat -> lang.getId() == cat.getLanguage().getId())
                        .findAny().orElseThrow(() -> new CategoryNotFoundException("Cannot find category with id = "
                                + id + "for language with id " + lang.getId()));
                langToCategory.put(lang.getId(), category);
            });

            req.getSession().setAttribute(LANG_TO_CATEGORY_ATTRIBUTE, langToCategory);

            ActionUtils.getDataForChangeCategoryForm(req, id);
            req.setAttribute(ACTION_ATTRIBUTE, CHANGE_CATEGORY_FORM_ACTION);
            RoutingUtils.forwardToPage(ADD_CHANGE_CATEGORY_JSP, req, resp);
        } else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
