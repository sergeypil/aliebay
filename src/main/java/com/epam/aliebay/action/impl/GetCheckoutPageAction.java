package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.JspNameConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class GetCheckoutPageAction implements Action {
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
        if (user.getStatus().getId() != ID_USER_STATUS_BANNED) {
            if (ActionUtils.isCountOfProductsInCartMatchesCountInDb(req, productDao)) {
                RoutingUtils.forwardToPage(JspNameConstants.CHECKOUT_JSP, req, resp);
            } else {
                RoutingUtils.forwardToErrorPage(HttpServletResponse.SC_BAD_REQUEST, ERROR_400_TITLE_PRODUCT_NOT_ENOUGH,
                        ERROR_400_MESSAGE_PRODUCT_NOT_ENOUGH, req, resp);
            }
        } else {
            RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
        }
    }
}
