package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

import static com.epam.aliebay.constant.ActionConstants.GET_ACCOUNT_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.UPDATE_TAB;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class UpdateUserAction implements Action {
    private final UserDao userDao = PostgreSqlDaoFactory.getInstance().getUserDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean areAllParametersValid = true;
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(FIRST_NAME_PARAMETER))) {
            req.setAttribute(WRONG_FIRST_NAME_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(LAST_NAME_PARAMETER))) {
            req.setAttribute(WRONG_LAST_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidUsername(req.getParameter(USERNAME_PARAMETER))) {
            req.setAttribute(WRONG_USERNAME_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidPhoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER))) {
            req.setAttribute(WRONG_PHONE_NUMBER_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidBirthDate(req.getParameter(BIRTH_DATE_PARAMETER))) {
            req.setAttribute(WRONG_BIRTH_DATE_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidEmail(req.getParameter(EMAIL_PARAMETER))) {
            req.setAttribute(WRONG_EMAIL_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!areAllParametersValid) {
            req.setAttribute(ACTIVE_TAB_ATTRIBUTE, UPDATE_TAB);
            RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
        } else {
            User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
            user.setFirstName(req.getParameter(FIRST_NAME_PARAMETER));
            user.setLastName(req.getParameter(LAST_NAME_PARAMETER));
            user.setUsername(req.getParameter(USERNAME_PARAMETER));
            user.setBirthDate(Date.valueOf(req.getParameter(BIRTH_DATE_PARAMETER)));
            user.setPhoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER));
            user.setEmail(req.getParameter(EMAIL_PARAMETER));
            userDao.updateUser(user, user.getId());
            HttpSession session = req.getSession();
            session.setAttribute(CURRENT_USER_ATTRIBUTE, user);
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ACCOUNT_PAGE_ACTION);
        }
    }
}
