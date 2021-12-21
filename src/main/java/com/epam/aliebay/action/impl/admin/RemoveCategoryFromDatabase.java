package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_CATEGORIES_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.ERROR_403_MESSAGE;
import static com.epam.aliebay.constant.OtherConstants.ERROR_403_TITLE;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class RemoveCategoryFromDatabase implements Action {
    private static final Logger LOGGER = Logger.getLogger(RemoveCategoryFromDatabase.class);
    private final CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (AccessValidator.isAccessPermitted(req)) {
            int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
            categoryDao.deleteCategoryById(id);
            LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                    + " deleted category with id " + id + " from database");
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_CATEGORIES_PAGE_ACTION);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_403_TITLE);
            req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_403_MESSAGE);
            RoutingUtils.forwardToPage(ERROR_JSP, req, resp);
        }
    }
}
