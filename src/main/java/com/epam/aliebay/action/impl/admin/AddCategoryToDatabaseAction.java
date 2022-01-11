package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.dto.CategoryDto;
import com.epam.aliebay.util.*;
import com.epam.aliebay.validation.form.CategoryConstraintValidatorsStorage;
import com.epam.aliebay.validation.form.FormValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_CATEGORIES_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.HOST_NAME_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_CATEGORY_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class AddCategoryToDatabaseAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddCategoryToDatabaseAction.class);
    private final CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CategoryDto categoryDto = ActionUtils.createCategoryDtoFromRequest(req);
        Map<Integer, Set<String>> langToValidationErrorAttributes = getValidationErrorAttributes(req, categoryDto);
        if (langToValidationErrorAttributes.values().stream().anyMatch(set -> !set.isEmpty())) {
            ActionUtils.setAttributesWhenValidationErrorOnCategoryForm(req, categoryDto, langToValidationErrorAttributes, ADD_CATEGORY_FORM_ACTION);
            RoutingUtils.forwardToPage(ADD_CHANGE_CATEGORY_JSP, req, resp);
        } else {
            byte[] imageBytes = getImageBytesOfCategory(req, categoryDto);
            categoryDao.saveCategory(categoryDto, imageBytes);
            LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                    + " added category " + categoryDto.getLangIdToCategoryName().entrySet() + " to database");
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_CATEGORIES_PAGE_ACTION);
        }
    }

    private Map<Integer, Set<String>> getValidationErrorAttributes(HttpServletRequest req, CategoryDto categoryDto) {
        Map<Integer, Set<String>> langToValidationErrorAttributes = ActionUtils.createMapLangToValidationsErrorAttributes(categoryDto);
        CategoryConstraintValidatorsStorage categoryConstraintValidatorsStorage =
                CategoryConstraintValidatorsStorage.createDefault(categoryDto, langToValidationErrorAttributes, req);
        FormValidator.validate(categoryConstraintValidatorsStorage);
        return langToValidationErrorAttributes;
    }

    private byte[] getImageBytesOfCategory(HttpServletRequest req, CategoryDto categoryDto) throws IOException {
        return ValidationUtils.isImageLoaded(categoryDto.getImagePart()) ?
                        AppUtils.readImageBytes(categoryDto.getImagePart().getInputStream()) :
                        AppUtils.readImageBytes(req.getServletContext().getResourceAsStream(PATH_TO_DEFAULT_IMAGE));
    }
}
