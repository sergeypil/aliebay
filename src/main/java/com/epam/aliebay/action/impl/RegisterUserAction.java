package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.dao.Interface.UserStatusDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.entity.UserStatus;
import com.epam.aliebay.exception.UserNotFoundException;
import com.epam.aliebay.exception.UserStatusNotFoundException;
import com.epam.aliebay.dto.RegisterDto;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.validation.form.FormValidator;
import com.epam.aliebay.validation.form.RegisterFormConstraintValidatorsStorage;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.epam.aliebay.constant.ActionConstants.GET_HOME_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.CUSTOMER_ROLE;
import static com.epam.aliebay.constant.OtherConstants.ID_USER_STATUS_ACTIVE;
import static com.epam.aliebay.constant.JspNameConstants.REGISTER_JSP;

public class RegisterUserAction implements Action {
    private final UserDao userDao = DaoFactory.getDaoFactory().getUserDao();
    private final UserStatusDao userStatusDao = DaoFactory.getDaoFactory().getUserStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RegisterDto registerDto = ActionUtils.createRegisterDtoFromRequest(req);
        Set<String> validationErrorAttributes = getValidationErrorAttributes(registerDto);
        checkIfUsernameExist(registerDto, validationErrorAttributes, req);
        checkIfEmailExist(registerDto, validationErrorAttributes, req);
        if (validationErrorAttributes.size() != 0 ) {
            validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
            req.setAttribute(REGISTER_DTO_ATTRIBUTE, registerDto);
            RoutingUtils.forwardToPage(REGISTER_JSP, req, resp);
        } else {
            User user = mapRegisterDtoToUser(registerDto, req);
            userDao.saveUser(user);
            User savedUser = userDao.getUserByUsername(user.getUsername(),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                    () -> new UserNotFoundException("Cannot find user with username " + user.getUsername()));
            HttpSession session = req.getSession();
            session.setAttribute(CURRENT_USER_ATTRIBUTE, savedUser);
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_HOME_PAGE_ACTION);
        }
    }

    private void checkIfUsernameExist(RegisterDto registerDto, Set<String> validationErrorAttributes, HttpServletRequest req) {
        if(!validationErrorAttributes.contains(WRONG_USERNAME_ATTRIBUTE)) {
            Optional<User> optionalUser = userDao.getUserByUsername(registerDto.getUsername(),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            if (optionalUser.isPresent()) {
                validationErrorAttributes.add(ERROR_USERNAME_EXIST_ATTRIBUTE);
            }
        }
    }

    private void checkIfEmailExist(RegisterDto registerDto, Set<String> validationErrorAttributes, HttpServletRequest req) {
        if(!validationErrorAttributes.contains(WRONG_EMAIL_ATTRIBUTE)) {
            Optional<User> optionalUser = userDao.getUserByEmail(registerDto.getEmail(),
                    (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
            if (optionalUser.isPresent()) {
                validationErrorAttributes.add(ERROR_EMAIL_EXIST_ATTRIBUTE);
            }
        }
    }

    private Set<String> getValidationErrorAttributes(RegisterDto registerDto) {
        Set<String> validationErrorAttributes = new HashSet<>();
        RegisterFormConstraintValidatorsStorage registerFormConstraintValidatorsStorage =
                RegisterFormConstraintValidatorsStorage.createDefaultForRegisterPage(registerDto, validationErrorAttributes);
        FormValidator.validate(registerFormConstraintValidatorsStorage);
        return validationErrorAttributes;
    }

    private User mapRegisterDtoToUser(RegisterDto registerDto, HttpServletRequest req) {
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setBirthDate(Date.valueOf(registerDto.getBirthDate()));
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        String hashedPassword = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        UserStatus orderStatus = userStatusDao.getUserStatusById(ID_USER_STATUS_ACTIVE,
                (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                () -> new UserStatusNotFoundException("Cannot find user status with id = " + ID_USER_STATUS_ACTIVE));
        user.setStatus(orderStatus);
        user.setRole(CUSTOMER_ROLE);
        return user;
    }
}
