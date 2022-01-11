package com.epam.aliebay.filter;

import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.TARGET_PAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.*;

public class CheckLoginFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session.getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            session.setAttribute(TARGET_PAGE_ATTRIBUTE, req.getServletPath() + req.getPathInfo());
            //resp.sendRedirect(LOGIN_PAGE);
            RoutingUtils.forwardToErrorPage(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else {
            chain.doFilter(req, resp);
        }
    }
}
