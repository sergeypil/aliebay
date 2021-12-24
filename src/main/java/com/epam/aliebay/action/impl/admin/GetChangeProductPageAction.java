package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.ProductNotFoundException;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCT_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetChangeProductPageAction implements Action {
    private final ProductDao productDao = PostgreSqlDaoFactory.getInstance().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else if (AccessValidator.isAccessPermitted(req)) {
            int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
            Product product = productDao.getProductById(id).orElseThrow(
                    () -> new ProductNotFoundException("Cannot find product with id = " + id));
            req.getSession().setAttribute(EDITED_PRODUCT_ATTRIBUTE, product);
            ActionUtils.getDataForProductForm(req);
            req.setAttribute(ACTION_ATTRIBUTE, CHANGE_PRODUCT_FORM_ACTION);
            RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCT_JSP, req, resp);
        } else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
