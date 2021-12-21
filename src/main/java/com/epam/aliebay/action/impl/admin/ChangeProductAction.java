package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_PRODUCTS_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCT_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class ChangeProductAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ChangeProductAction.class);
    private final ProductDao productDao = PostgreSqlDaoFactory.getInstance().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (AccessValidator.isAccessPermitted(req)) {
            Product editedProduct = (Product) req.getSession().getAttribute(EDITED_PRODUCT_ATTRIBUTE);
            boolean areAllParametersValid = ActionUtils.validateRequestFromProductForm(req, editedProduct);
            if (!areAllParametersValid) {
                req.getSession().setAttribute(EDITED_PRODUCT_ATTRIBUTE, editedProduct);
                req.setAttribute(ACTION_ATTRIBUTE, CHANGE_PRODUCT_FORM_ACTION);
                RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCT_JSP, req, resp);
            } else {
                req.getSession().removeAttribute(EDITED_PRODUCT_ATTRIBUTE);
                productDao.updateProduct(editedProduct, editedProduct.getId());
                LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                        + " changed product " + editedProduct.getName() + " in database");
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_PRODUCTS_PAGE_ACTION);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_403_TITLE);
            req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_403_MESSAGE);
            RoutingUtils.forwardToPage(ERROR_JSP, req, resp);
        }
    }
}
