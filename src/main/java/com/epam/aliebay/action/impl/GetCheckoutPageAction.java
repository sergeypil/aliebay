package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.constant.JspNameConstants;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.ID_USER_STATUS_BANNED;

public class GetCheckoutPageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
        if (user.getStatus().getId() == ID_USER_STATUS_BANNED) {
            RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
        }
        else {
            RoutingUtils.forwardToPage(JspNameConstants.CHECKOUT_JSP, req, resp);
        }
    }
}
