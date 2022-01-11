package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.dto.ProductDto;
import com.epam.aliebay.util.*;
import com.epam.aliebay.validation.form.FormValidator;
import com.epam.aliebay.validation.form.ProductConstraintValidatorsStorage;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_PRODUCTS_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCT_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class ChangeProductAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ChangeProductAction.class);
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProductDto productDto = ActionUtils.createProductDtoFromRequest(req);
        Set<String> validationErrorAttributes = getValidationErrorAttributes(req, productDto);
        if (validationErrorAttributes.size() != 0) {
            ActionUtils.setAttributesWhenValidationErrorOnProductForm(req, productDto, validationErrorAttributes,
                    CHANGE_PRODUCT_FORM_ACTION);
            RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCT_JSP, req, resp);
        } else {
            Product product = ActionUtils.mapProductDtoToProduct(req, productDto);
            product.setImage(AppUtils.mapImageBytesToImageWithPrefix(getImageBytesOfProduct(req, productDto)));
            ActionUtils.deleteAttributesFromSessionAfterFinishEdit(req);
            productDao.updateProduct(product, (Long) req.getSession().getAttribute(EDITED_PRODUCT_ID_ATTRIBUTE));
            LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                    + " changed product " + product.getName() + " in database");
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_PRODUCTS_PAGE_ACTION);
        }
    }

    private Set<String> getValidationErrorAttributes(HttpServletRequest req, ProductDto productDto) {
        Set<String> validationErrorAttributes = new HashSet<>();
        ProductConstraintValidatorsStorage ValidatorsStorage =
                ProductConstraintValidatorsStorage.createDefault(productDto, validationErrorAttributes, req);
        FormValidator.validate(ValidatorsStorage);
        return validationErrorAttributes;
    }

    private byte[] getImageBytesOfProduct(HttpServletRequest req, ProductDto productDto) throws IOException {
        return ValidationUtils.isImageLoaded(productDto.getImagePart()) ?
                        AppUtils.readImageBytes(productDto.getImagePart().getInputStream()) :
                        (byte[]) req.getSession().getAttribute(EDITED_PRODUCT_IMAGE_BYTES_ATTRIBUTE);
    }
}
