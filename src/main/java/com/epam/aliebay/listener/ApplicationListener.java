package com.epam.aliebay.listener;

import com.epam.aliebay.dao.Interface.LanguageDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.connectionpool.ConnectionPool;
import com.epam.aliebay.entity.Language;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.APP_LANGUAGES_ATTRIBUTE;

public class ApplicationListener implements ServletContextListener {
    private final LanguageDao languageDao = PostgreSqlDaoFactory.getInstance().getLanguageDao();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        List<Language> appLanguages =  languageDao.getAllLanguages();
        sce.getServletContext().setAttribute(APP_LANGUAGES_ATTRIBUTE, appLanguages);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().shutDownConnections();
    }
}
