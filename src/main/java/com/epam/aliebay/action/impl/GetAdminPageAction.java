package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.OtherConstants.ERROR_401_MESSAGE;

public class GetAdminPageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        if (AccessValidator.isAccessPermitted(req)) {
            RoutingUtils.forwardToPage(ADMIN_JSP, req, resp);
        } else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
