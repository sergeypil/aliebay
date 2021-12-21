package com.epam.aliebay.filter;

import com.epam.aliebay.constant.AttributeConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.CURRENT_LOCALE_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.LANGUAGE_EN;

public class LanguageFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Locale locale = req.getLocale();
        HttpSession session = req.getSession();
        if (session.getAttribute(CURRENT_LOCALE_ATTRIBUTE) == null) {
            if( locale == null) {
                session.setAttribute(CURRENT_LOCALE_ATTRIBUTE, Locale.ENGLISH);
                session.setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, LANGUAGE_EN);
            }
            else {
                session.setAttribute(CURRENT_LOCALE_ATTRIBUTE, locale);
                session.setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, locale.getLanguage());
            }
        }
        chain.doFilter(req, resp);
    }
}
