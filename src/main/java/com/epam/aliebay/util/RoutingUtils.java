package com.epam.aliebay.util;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_PAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.TEMPLATE_JSP;

public final class RoutingUtils {
    public static void sendJSON(JSONObject json, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().println(json.toString());
        resp.getWriter().close();
    }
    public static void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(CURRENT_PAGE_ATTRIBUTE, jspPage);
        req.getRequestDispatcher(TEMPLATE_JSP).forward(req, resp);
    }
}
