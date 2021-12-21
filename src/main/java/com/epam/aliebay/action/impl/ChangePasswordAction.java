package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.constant.JspNameConstants;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.exception.UserNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.util.ValidationUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.CHANGE_PASSWORD_TAB;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class ChangePasswordAction implements Action {
    private final UserDao userDao = PostgreSqlDaoFactory.getInstance().getUserDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean areAllParametersValid = true;
        User user = (User) req.getSession().getAttribute(AttributeConstants.CURRENT_USER_ATTRIBUTE);
        User userFromDb = userDao.getUserById(user.getId(),
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                () -> new UserNotFoundException("Cannot find user with id = " + user.getId()));
        if (!BCrypt.checkpw(req.getParameter(CURRENT_PASSWORD_PARAMETER), userFromDb.getPassword())) {
            req.setAttribute(WRONG_CURRENT_PASSWORD_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidPassword(req.getParameter(NEW_PASSWORD_PARAMETER))) {
            req.setAttribute(WRONG_PASSWORD_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidConfirmedPassword(req.getParameter(CONFIRMED_PASSWORD_PARAMETER),
                req.getParameter(CONFIRMED_PASSWORD_PARAMETER))) {
            req.setAttribute(WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!areAllParametersValid) {
            req.setAttribute(ACTIVE_TAB_ATTRIBUTE, CHANGE_PASSWORD_TAB);
            RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
        } else {
            String hashedNewPassword = BCrypt.hashpw(req.getParameter(NEW_PASSWORD_PARAMETER), BCrypt.gensalt());
            userFromDb.setPassword(hashedNewPassword);
            userDao.updateUser(userFromDb, userFromDb.getId());
            RoutingUtils.forwardToPage(JspNameConstants.ACCOUNT_JSP, req, resp);
        }
    }
}
