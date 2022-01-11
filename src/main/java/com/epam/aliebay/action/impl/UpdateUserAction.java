package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.dto.RegisterDto;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.validation.form.FormValidator;
import com.epam.aliebay.validation.form.RegisterFormConstraintValidatorsStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

import static com.epam.aliebay.constant.ActionConstants.GET_ACCOUNT_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.UPDATE_TAB;

public class UpdateUserAction implements Action {
    private final UserDao userDao = DaoFactory.getDaoFactory().getUserDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RegisterDto registerDto = ActionUtils.createRegisterDtoFromRequest(req);
        User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
        Set<String> validationErrorAttributes = getValidationErrorAttributes(registerDto);
        checkIfUsernameExist(registerDto, validationErrorAttributes, req);
        checkIfEmailExist(registerDto, validationErrorAttributes, req);
        if (validationErrorAttributes.size() != 0 ) {
            req.setAttribute(ACTIVE_TAB_ATTRIBUTE, UPDATE_TAB);
            validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
            req.setAttribute(REGISTER_DTO_ATTRIBUTE, registerDto);
            RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
        } else {
            mapUpdateRegisterDtoToUser(registerDto, user);
            userDao.updateUser(user, user.getId());
            HttpSession session = req.getSession();
            session.setAttribute(CURRENT_USER_ATTRIBUTE, user);
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ACCOUNT_PAGE_ACTION);
        }
    }

    private void checkIfUsernameExist(RegisterDto registerDto, Set<String> validationErrorAttributes, HttpServletRequest req) {
        if(!validationErrorAttributes.contains(WRONG_USERNAME_ATTRIBUTE)) {
            User currentUser = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
            Optional<User> optionalUser = userDao.getUserByUsername(registerDto.getUsername(),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            if (optionalUser.isPresent()) {
                if (currentUser != null && optionalUser.get().getUsername().equals(currentUser.getUsername())) {
                    return;
                }
                validationErrorAttributes.add(ERROR_USERNAME_EXIST_ATTRIBUTE);
            }
        }
    }

    private void checkIfEmailExist(RegisterDto registerDto, Set<String> validationErrorAttributes, HttpServletRequest req) {
        if(!validationErrorAttributes.contains(WRONG_EMAIL_ATTRIBUTE)) {
            User currentUser = (User)req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
            Optional<User> optionalUser = userDao.getUserByEmail(registerDto.getEmail(),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            if (optionalUser.isPresent()) {
                if (currentUser != null && optionalUser.get().getEmail().equals(currentUser.getEmail())) {
                    return;
                }
                validationErrorAttributes.add(ERROR_EMAIL_EXIST_ATTRIBUTE);
            }
        }
    }

    private Set<String> getValidationErrorAttributes(RegisterDto registerDto) {
        Set<String> validationErrorAttributes = new HashSet<>();
        RegisterFormConstraintValidatorsStorage registerFormConstraintValidatorsStorage =
                RegisterFormConstraintValidatorsStorage.createDefaultForUpdatePage(registerDto, validationErrorAttributes);
        FormValidator.validate(registerFormConstraintValidatorsStorage);
        return validationErrorAttributes;
    }

    private void mapUpdateRegisterDtoToUser(RegisterDto registerDto, User user) {
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setBirthDate(Date.valueOf(registerDto.getBirthDate()));
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setEmail(registerDto.getEmail());
    }
}
