package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.constant.JspNameConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.dto.ChangePasswordDto;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.validation.form.ChangePasswordConstraintValidatorsStorage;
import com.epam.aliebay.validation.form.FormValidator;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.ACTIVE_TAB_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.WRONG_CURRENT_PASSWORD_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.CHANGE_PASSWORD_TAB;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public class ChangePasswordAction implements Action {
    private final UserDao userDao = DaoFactory.getDaoFactory().getUserDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ChangePasswordDto changePasswordDto = createChangePasswordDto(req);
        Set<String> validationErrorAttributes = new HashSet<>();
        ChangePasswordConstraintValidatorsStorage changePasswordConstraintValidatorsStorage =
                ChangePasswordConstraintValidatorsStorage.createDefault(changePasswordDto, validationErrorAttributes);
        FormValidator.validate(changePasswordConstraintValidatorsStorage);

        User user = (User) req.getSession().getAttribute(AttributeConstants.CURRENT_USER_ATTRIBUTE);
        if (!BCrypt.checkpw(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            validationErrorAttributes.add(WRONG_CURRENT_PASSWORD_ATTRIBUTE);
        }

        if (validationErrorAttributes.size() != 0) {
            validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
            req.setAttribute(ACTIVE_TAB_ATTRIBUTE, CHANGE_PASSWORD_TAB);
            RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
        } else {
            String hashedNewPassword = BCrypt.hashpw(req.getParameter(NEW_PASSWORD_PARAMETER), BCrypt.gensalt());
            user.setPassword(hashedNewPassword);
            userDao.updateUser(user, user.getId());
            RoutingUtils.forwardToPage(JspNameConstants.ACCOUNT_JSP, req, resp);
        }
    }

    private ChangePasswordDto createChangePasswordDto(HttpServletRequest req) {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setCurrentPassword(req.getParameter(CURRENT_PASSWORD_PARAMETER));
        changePasswordDto.setNewPassword(req.getParameter(NEW_PASSWORD_PARAMETER));
        changePasswordDto.setConfirmedPassword(req.getParameter(CONFIRMED_PASSWORD_PARAMETER));
        return changePasswordDto;
    }
}
