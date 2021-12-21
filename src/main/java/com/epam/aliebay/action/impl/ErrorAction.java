package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.ERROR_MESSAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_404_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;

public class ErrorAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        req.getRequestDispatcher(ERROR_404_JSP).forward(req, resp);
    }
}
