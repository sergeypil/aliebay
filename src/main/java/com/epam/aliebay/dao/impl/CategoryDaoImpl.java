package com.epam.aliebay.dao.impl;


import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.entity.Category;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.aliebay.constant.OtherConstants.INDEX_OF_MAP_LANG_TO_CATEGORY_WHERE_STORE_ENTERED_DATA;
import static com.epam.aliebay.constant.OtherConstants.LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE;

public class CategoryDaoImpl extends AbstractBaseDao implements CategoryDao {
    private static final ResultSetHandler<Category> categoryResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.CATEGORY_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<Category>> categoriesResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.CATEGORY_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Integer> idResultSetHandler =
            ResultSetHandlerFactory.getIntResultSetHandler();

    private static final String SELECT_ALL_CATEGORIES_ALL_LANGUAGES_QUERY = "SELECT c.id as id_category, ci.name as name_category, c.image, " +
            "c.id_parent, ci.id_language, l.code, l.name as name_language FROM category c LEFT JOIN category_detail ci ON c.id = ci.id INNER JOIN language l ON " +
            "ci.id_language = l.id ORDER BY c.id";
    private static final String SELECT_CATEGORY_BY_ID_ALL_LANGUAGES_QUERY = "SELECT c.id as id_category, ci.name as name_category, c.image, " +
            "c.id_parent, ci.id_language, l.code, l.name as name_language FROM category c LEFT JOIN category_detail ci ON c.id = ci.id INNER JOIN language l ON " +
            "ci.id_language = l.id WHERE c.id = ?";
    private static final String SELECT_ALL_CATEGORIES_QUERY = "SELECT c.id as id_category, ci.name as name_category, c.image, " +
            "c.id_parent, ci.id_language, l.code, l.name as name_language FROM category c LEFT JOIN category_detail ci ON c.id = ci.id INNER JOIN language l ON " +
            "ci.id_language = l.id WHERE l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY c.id";
    private static final String SELECT_LEAF_CATEGORIES_QUERY = "SELECT c.id as id_category, ci.name as name_category, c.image, " +
            "c.id_parent, ci.id_language, l.code, l.name as name_language FROM category c LEFT JOIN category_detail ci ON c.id = ci.id INNER JOIN language l ON " +
            "ci.id_language = l.id WHERE c.id NOT IN (SELECT DISTINCT id_parent FROM category) AND l.id in " +
            "(SELECT l.id FROM language l WHERE l.code=?) ORDER BY c.id";
    private static final String SELECT_ALL_CATEGORIES_BY_PARENT_ID_QUERY = "SELECT c.id as id_category, ci.name as name_category, c.image, " +
            "c.id_parent, ci.id_language, l.code, l.name as name_language FROM category c LEFT JOIN category_detail ci ON c.id = ci.id INNER JOIN language l ON " +
            "ci.id_language = l.id WHERE c.id_parent=? AND l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY c.id";
    private static final String SELECT_CATEGORY_BY_ID_QUERY = "SELECT c.id as id_category, ci.name as name_category, c.image, " +
            "c.id_parent, ci.id_language, l.code, l.name as name_language FROM category c LEFT JOIN category_detail ci ON c.id = ci.id INNER JOIN language l ON " +
            "ci.id_language = l.id WHERE c.id=? AND l.id in (SELECT l.id FROM language l WHERE l.code=?)";
    private static final String INSERT_CATEGORY_QUERY = "INSERT INTO category (image, id_parent) VALUES (?,?)";
    private static final String INSERT_CATEGORY_DETAIL_QUERY = "INSERT INTO category_detail (id, id_language, name) VALUES (?,?,?)";
    private static final String UPDATE_CATEGORY_QUERY = "UPDATE category SET image=?, id_parent=? WHERE id = ?";
    private static final String UPDATE_CATEGORY_DETAIL_QUERY = "UPDATE category_detail set name=? WHERE id = ? AND id_language = ?";
    private static final String DELETE_CATEGORY_BY_ID_QUERY = "DELETE FROM category WHERE id=?";
    private static final String DELETE_CATEGORY_DETAILS_BY_ID_QUERY = "DELETE FROM category_detail WHERE id=?";

    @Override
    public List<Category> getAllCategoriesAllLanguages() {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_ALL_LANGUAGES_QUERY, categoriesResultSetHandler);
    }

    @Override
    public List<Category> getCategoryByIdAllLanguages(int id) {
        return JdbcTemplate.select(SELECT_CATEGORY_BY_ID_ALL_LANGUAGES_QUERY, categoriesResultSetHandler, id);
    }

    @Override
    public Optional<Category> getCategoryById(int id, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_CATEGORY_BY_ID_QUERY, categoryResultSetHandler, id, language));
    }

    public List<Category> getAllCategories(String language) {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_QUERY, categoriesResultSetHandler, language);
    }

    @Override
    public List<Category> getLeafCategories(String language) {
        return JdbcTemplate.select(SELECT_LEAF_CATEGORIES_QUERY, categoriesResultSetHandler, language);
    }

    public List<Category> getAllCategoriesByParentId(int idParent, String language) {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_BY_PARENT_ID_QUERY, categoriesResultSetHandler, idParent, language);
    }

    @Override
    public void saveCategory(Map<Integer, Category> langToCategory) {
        String imageWithoutCodePrefix = langToCategory.get(1).getImage().substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        byte[] imageBytes = Base64.getDecoder().decode(imageWithoutCodePrefix);
        doInTransaction(connection -> {
            int idCategory = JdbcTemplate.executeUpdateInTransactionWithGenKeys(connection, INSERT_CATEGORY_QUERY,
                    idResultSetHandler, imageBytes, langToCategory.get(1).getParentCategoryId());
            langToCategory.entrySet()
                    .forEach(el -> JdbcTemplate.executeUpdateInTransaction(connection, INSERT_CATEGORY_DETAIL_QUERY,
                            idCategory, el.getKey(), el.getValue().getName()));
        });
    }

    @Override
    public void updateCategory(Map<Integer, Category> langToCategory, int id) {
        String imageWithoutCodePrefix = langToCategory.get(INDEX_OF_MAP_LANG_TO_CATEGORY_WHERE_STORE_ENTERED_DATA)
                .getImage().substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        byte[] imageBytes = Base64.getDecoder().decode(imageWithoutCodePrefix);
        doInTransaction(connection -> {
            JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_CATEGORY_QUERY, imageBytes,
                    langToCategory.get(INDEX_OF_MAP_LANG_TO_CATEGORY_WHERE_STORE_ENTERED_DATA).getParentCategoryId(), id);
            langToCategory.entrySet()
                    .forEach(el -> JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_CATEGORY_DETAIL_QUERY,
                            el.getValue().getName(), id, el.getKey()));
            });
    }

    @Override
    public void deleteCategoryById(int id) {
        doInTransaction(connection -> {
                JdbcTemplate.executeUpdateInTransaction(connection, DELETE_CATEGORY_DETAILS_BY_ID_QUERY, id);
                JdbcTemplate.executeUpdateInTransaction(connection, DELETE_CATEGORY_BY_ID_QUERY, id);
        });
    }
}
