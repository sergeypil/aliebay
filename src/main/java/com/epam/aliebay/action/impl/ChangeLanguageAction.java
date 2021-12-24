package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.epam.aliebay.constant.ActionConstants.GET_HOME_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.LANGUAGE_PARAMETER;

public class ChangeLanguageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!ValidationUtils.isParameterNullOrEmpty(req.getParameter(LANGUAGE_PARAMETER))) {
            String currentLanguage = req.getParameter(LANGUAGE_PARAMETER);
            Locale currentLocale = new Locale(currentLanguage);
            req.getSession().setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, currentLanguage);
            req.getSession().setAttribute(CURRENT_LOCALE_ATTRIBUTE, currentLocale);
            String prevURL = req.getHeader(REFERER_HEADER);
            if (prevURL == null) {
                prevURL = req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_HOME_PAGE_ACTION;
            }
            resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            resp.sendRedirect(prevURL);
        }
    }
}
