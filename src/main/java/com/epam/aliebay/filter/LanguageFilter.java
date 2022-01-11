package com.epam.aliebay.filter;

import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.LanguageDao;
import com.epam.aliebay.entity.Language;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.OtherConstants.LANGUAGE_EN;

public class LanguageFilter extends AbstractFilter {
    private final LanguageDao languageDao = DaoFactory.getDaoFactory().getLanguageDao();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        List<Language> appLanguages =  languageDao.getAllLanguages();
        req.getSession().setAttribute(APP_LANGUAGES_ATTRIBUTE, appLanguages);
        Locale locale = req.getLocale();
        HttpSession session = req.getSession();
        if (session.getAttribute(CURRENT_LOCALE_ATTRIBUTE) == null) {
            Optional<Language> optionalLanguage =  appLanguages.stream()
                    .filter(lang -> lang.getCode().equalsIgnoreCase(locale.getLanguage()))
                    .findAny();
            if (optionalLanguage.isPresent()) {
                session.setAttribute(CURRENT_LOCALE_ATTRIBUTE, locale);
                session.setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, locale.getLanguage());
            }
            else {
                session.setAttribute(CURRENT_LOCALE_ATTRIBUTE, Locale.ENGLISH);
                session.setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, LANGUAGE_EN);
            }
        }
        chain.doFilter(req, resp);
    }
}
