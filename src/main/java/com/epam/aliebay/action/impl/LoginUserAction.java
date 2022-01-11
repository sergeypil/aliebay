package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.validation.form.FormValidator;
import com.epam.aliebay.validation.form.LoginConstraintValidatorsStorage;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_PAGE_ACTION;
import static com.epam.aliebay.constant.ActionConstants.GET_HOME_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.LOGIN_JSP;
import static com.epam.aliebay.constant.OtherConstants.ADMIN_ROLE;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.PASSWORD_PARAMETER;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.USERNAME_OR_EMAIL_PARAMETER;

public class LoginUserAction implements Action {
    private final UserDao userDao = DaoFactory.getDaoFactory().getUserDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String usernameOrEmail = req.getParameter(USERNAME_OR_EMAIL_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        Set<String> validationErrorAttributes = getValidationErrorAttributes(usernameOrEmail, password);
        if (validationErrorAttributes.size() != 0) {
            validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
            RoutingUtils.forwardToPage(LOGIN_JSP, req, resp);
        } else {
            Optional<User> optionalUser = getOptionalUserByCredentials(usernameOrEmail, password, req);
            if (optionalUser.isPresent()) {
                req.removeAttribute(AUTHENTICATION_ERROR_ATTRIBUTE);
                HttpSession session = req.getSession();
                session.setAttribute(CURRENT_USER_ATTRIBUTE, optionalUser.get());
                redirectAfterLogin(req, resp, session, optionalUser.get());
            } else {
                req.setAttribute(AUTHENTICATION_ERROR_ATTRIBUTE, true);
                RoutingUtils.forwardToPage(LOGIN_JSP, req, resp);
            }
        }
    }

    private Set<String> getValidationErrorAttributes(String usernameOrEmail, String password) {
        Set<String> validationErrorAttributes = new HashSet<>();
        LoginConstraintValidatorsStorage loginConstraintValidatorsStorage =
                LoginConstraintValidatorsStorage.createDefault(usernameOrEmail, password, validationErrorAttributes);
        FormValidator.validate(loginConstraintValidatorsStorage);
        return validationErrorAttributes;
    }

    private void redirectAfterLogin(HttpServletRequest req, HttpServletResponse resp, HttpSession session, User user) throws IOException {
        if (session.getAttribute(TARGET_PAGE_ATTRIBUTE) != null) {
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) +
                    (String) session.getAttribute(TARGET_PAGE_ATTRIBUTE));
        } else if (user.getRole().equals(ADMIN_ROLE)) {
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_PAGE_ACTION);
        } else {
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_HOME_PAGE_ACTION);
        }
    }

    private Optional<User> getOptionalUserByCredentials(String usernameOrEmail, String password, HttpServletRequest req) {
        Optional<User> optionalUser = userDao.getUserByUsername(usernameOrEmail, (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        if (!optionalUser.isPresent()) {
            optionalUser = userDao.getUserByEmail(usernameOrEmail, (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return optionalUser;
            } else return Optional.empty();
        } else return Optional.empty();
    }
}
