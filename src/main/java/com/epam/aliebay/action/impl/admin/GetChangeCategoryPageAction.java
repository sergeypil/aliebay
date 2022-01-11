package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.dto.CategoryDto;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_CATEGORY_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetChangeCategoryPageAction implements Action {
    private final CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
        List<Category> editedCategories = categoryDao.getCategoryByIdAllLanguages(id);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setParentCategoryId(String.valueOf(editedCategories.get(INDEX_ONE_EDITED_CATEGORIES).getParentCategoryId()));
        editedCategories.forEach(cat -> categoryDto.getLangIdToCategoryName().put(cat.getLanguage().getId(), cat.getName()));

        req.getSession().setAttribute(CATEGORY_DTO_ATTRIBUTE, categoryDto);
        req.getSession().setAttribute(EDITED_CATEGORY_ID_ATTRIBUTE, editedCategories.get(INDEX_ONE_EDITED_CATEGORIES).getId());
        String imageWithoutCodePrefix = editedCategories.get(INDEX_ONE_EDITED_CATEGORIES)
                .getImage().substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        byte[] imageBytes = Base64.getDecoder().decode(imageWithoutCodePrefix);
        req.getSession().setAttribute(EDITED_CATEGORY_IMAGE_BYTES_ATTRIBUTE, imageBytes);

        ActionUtils.getDataForChangeCategoryForm(req, id);
        req.setAttribute(ACTION_ATTRIBUTE, CHANGE_CATEGORY_FORM_ACTION);
        RoutingUtils.forwardToPage(ADD_CHANGE_CATEGORY_JSP, req, resp);
    }
}
