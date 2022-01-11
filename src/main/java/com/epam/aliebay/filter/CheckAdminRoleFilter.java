package com.epam.aliebay.filter;

import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.TARGET_PAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.*;

public class CheckAdminRoleFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session.getAttribute(CURRENT_USER_ATTRIBUTE) != null) {
            User user = (User) session.getAttribute(CURRENT_USER_ATTRIBUTE);
            if (!user.getRole().equalsIgnoreCase("admin")) {
                session.setAttribute(TARGET_PAGE_ATTRIBUTE, req.getServletPath() + req.getPathInfo());
                RoutingUtils.forwardToErrorPage(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
            } else {
                chain.doFilter(req, resp);
            }
        }
    }
}
