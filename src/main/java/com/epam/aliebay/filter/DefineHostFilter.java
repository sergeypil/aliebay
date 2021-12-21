package com.epam.aliebay.filter;

import com.epam.aliebay.constant.AttributeConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefineHostFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String contextPath = req.getContextPath();
        req.setAttribute(AttributeConstants.CONTEXT_PATH_ATTRIBUTE, contextPath);

        StringBuffer url = req.getRequestURL();
        int startContext = url.indexOf(contextPath);
        String hostName = url.substring(0, startContext + contextPath.length());
        req.setAttribute(AttributeConstants.HOST_NAME_ATTRIBUTE, hostName);
        chain.doFilter(req, resp);
    }
}
