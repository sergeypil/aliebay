package com.epam.aliebay.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.TARGET_PAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.LOGIN_PAGE;

public class CheckAuthenticationFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session.getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            session.setAttribute(TARGET_PAGE_ATTRIBUTE, req.getServletPath() + req.getPathInfo());
            resp.sendRedirect(LOGIN_PAGE);
        }
        else {
            chain.doFilter(req, resp);
        }

    }
}
