package com.epam.aliebay.filter;


import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String defaultEncoding;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultEncoding = filterConfig.getInitParameter("defaultEncoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(defaultEncoding);
        response.setCharacterEncoding(defaultEncoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
