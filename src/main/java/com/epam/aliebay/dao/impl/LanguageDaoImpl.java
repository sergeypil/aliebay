package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.LanguageDao;
import com.epam.aliebay.entity.Language;

import java.util.List;

public class LanguageDaoImpl extends AbstractBaseDao implements LanguageDao {
    private static final ResultSetHandler<List<Language>> LANGUAGES_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.LANGUAGE_RESULT_SET_HANDLER);

    private static final String SELECT_ALL_CATEGORIES_QUERY = "SELECT id as language_id, code, name as language_name FROM language";

    @Override
    public List<Language> getAllLanguages() {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_QUERY, LANGUAGES_RESULT_SET_HANDLER);
    }

}
