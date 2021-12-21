package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.UserStatusDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.entity.UserStatus;
import com.epam.aliebay.exception.UserNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.util.ValidationUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import static com.epam.aliebay.constant.ActionConstants.GET_HOME_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.AttributeConstants.WRONG_PASSWORD_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.CUSTOMER_ROLE;
import static com.epam.aliebay.constant.OtherConstants.ID_USER_STATUS_ACTIVE;
import static com.epam.aliebay.constant.JspNameConstants.REGISTER_JSP;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class RegisterUserAction implements Action {
    private final UserDao userDao = PostgreSqlDaoFactory.getInstance().getUserDao();
    private final UserStatusDao userStatusDao = PostgreSqlDaoFactory.getInstance().getUserStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User editedUser = new User();
        boolean areAllParametersValid = true;
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(FIRST_NAME_PARAMETER))) {
            req.setAttribute(WRONG_FIRST_NAME_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedUser.setFirstName(req.getParameter(FIRST_NAME_PARAMETER));
        }
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(LAST_NAME_PARAMETER))) {
            req.setAttribute(WRONG_LAST_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedUser.setLastName(req.getParameter(LAST_NAME_PARAMETER));
        }
        if (!ValidationUtils.isValidUsername(req.getParameter(USERNAME_PARAMETER))) {
            req.setAttribute(WRONG_USERNAME_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            Optional<User> optionalUser = userDao.getUserByUsername(req.getParameter(USERNAME_PARAMETER),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            if (optionalUser.isPresent()) {
                req.setAttribute(ERROR_USERNAME_EXIST_ATTRIBUTE, true);
                areAllParametersValid = false;
            } else {
                editedUser.setUsername(req.getParameter(USERNAME_PARAMETER));
            }
        }
        if (!ValidationUtils.isValidPhoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER))) {
            req.setAttribute(WRONG_PHONE_NUMBER_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedUser.setPhoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER));
        }
        if (!ValidationUtils.isValidBirthDate(req.getParameter(BIRTH_DATE_PARAMETER))) {
            req.setAttribute(WRONG_BIRTH_DATE_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedUser.setBirthDate(Date.valueOf(req.getParameter(BIRTH_DATE_PARAMETER)));
        }
        if (!ValidationUtils.isValidEmail(req.getParameter(EMAIL_PARAMETER))) {
            req.setAttribute(WRONG_EMAIL_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            Optional<User> optionalUser = userDao.getUserByEmail(req.getParameter(EMAIL_PARAMETER),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            if (optionalUser.isPresent()) {
                req.setAttribute(ERROR_EMAIL_EXIST_ATTRIBUTE, true);
                areAllParametersValid = false;
            } else {
                editedUser.setEmail(req.getParameter(EMAIL_PARAMETER));
            }
        }

        if (!ValidationUtils.isValidPassword(req.getParameter(PASSWORD_PARAMETER))) {
            req.setAttribute(WRONG_PASSWORD_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!ValidationUtils.isValidConfirmedPassword(req.getParameter(PASSWORD_PARAMETER),
                req.getParameter(CONFIRMED_PASSWORD_PARAMETER))) {
            req.setAttribute(WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, true);
            areAllParametersValid = false;
        }
        if (!areAllParametersValid) {
            req.setAttribute(EDITED_USER_ATTRIBUTE, editedUser);
            RoutingUtils.forwardToPage(REGISTER_JSP, req, resp);
        } else {
            String hashedPassword = BCrypt.hashpw(req.getParameter(PASSWORD_PARAMETER), BCrypt.gensalt());
            editedUser.setPassword(hashedPassword);
            UserStatus orderStatus = userStatusDao.getUserStatusById(ID_USER_STATUS_ACTIVE, (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).get();
            editedUser.setStatus(orderStatus);
            editedUser.setRole(CUSTOMER_ROLE);
            userDao.saveUser(editedUser);
            User savedUser = userDao.getUserByUsername(editedUser.getUsername(),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                            () -> new UserNotFoundException("Cannot find user with username " + editedUser.getUsername()));
            HttpSession session = req.getSession();
            session.setAttribute(CURRENT_USER_ATTRIBUTE, savedUser);
            if (session.getAttribute(TARGET_PAGE_ATTRIBUTE) != null) {
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) +
                        (String) session.getAttribute(TARGET_PAGE_ATTRIBUTE));
            } else {
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_HOME_PAGE_ACTION);
            }
        }
    }
}
