package com.epam.aliebay.util;

import com.epam.aliebay.entity.User;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.JspNameConstants.TEMPLATE_JSP;
import static com.epam.aliebay.constant.OtherConstants.APPLICATION_JSON;

public final class RoutingUtils {
    private static final Logger LOGGER = Logger.getLogger(RoutingUtils.class);

    public static void sendJSON(JSONObject json, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(APPLICATION_JSON);
        resp.getWriter().println(json.toString());
        resp.getWriter().close();
    }
    public static void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(CURRENT_PAGE_ATTRIBUTE, jspPage);
        req.getRequestDispatcher(TEMPLATE_JSP).forward(req, resp);
    }

    public static void forwardToErrorPage(int statusCode, String errorTitle, String errorMessage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.error("Forward to error page after request: " + req.getRequestURL() +
                (req.getQueryString() != null ? "?" +req.getQueryString() : "") +
                (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) != null ? " by user with ID " +
                        ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getId() : "") +
                " with status code " + statusCode);
        resp.setStatus(statusCode);
        req.setAttribute(ERROR_TITLE_ATTRIBUTE, errorTitle);
        req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
        forwardToPage(ERROR_JSP, req, resp);
    }
}
