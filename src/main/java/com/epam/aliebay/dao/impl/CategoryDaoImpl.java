package com.epam.aliebay.dao.impl;


import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.dto.CategoryDto;

import java.util.List;

public class CategoryDaoImpl extends AbstractBaseDao implements CategoryDao {
    private static final ResultSetHandler<List<Category>> CATEGORIES_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.CATEGORY_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Integer> INT_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getIntResultSetHandler();

    private static final String SELECT_ALL_CATEGORIES_ALL_LANGUAGES_QUERY = "SELECT c.id AS category_id, cd.name AS " +
            "category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name " +
            "FROM category c LEFT JOIN category_detail cd ON c.id = cd.id INNER JOIN language l ON " +
            "cd.language_id = l.id ORDER BY c.id";
    private static final String SELECT_CATEGORY_BY_ID_ALL_LANGUAGES_QUERY = "SELECT c.id AS category_id, cd.name AS " +
            "category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name " +
            "FROM category c LEFT JOIN category_detail cd ON c.id = cd.id INNER JOIN language l ON " +
            "cd.language_id = l.id WHERE c.id = ?";
    private static final String SELECT_ALL_CATEGORIES_QUERY = "SELECT c.id AS category_id, cd.name AS category_name, " +
            "c.image AS category_image, " +
            "c.parent_id, cd.language_id, l.code, l.name AS language_name FROM category c LEFT JOIN category_detail cd " +
            "ON c.id = cd.id INNER JOIN language l ON " +
            "cd.language_id = l.id WHERE l.id in (SELECT l.id FROM language l WHERE l.code = ?) ORDER BY c.id";
    private static final String SELECT_LEAF_CATEGORIES_QUERY = "SELECT c.id AS category_id, cd.name AS category_name, " +
            "c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name FROM category c " +
            "LEFT JOIN category_detail cd ON c.id = cd.id INNER JOIN language l ON " +
            "cd.language_id = l.id WHERE c.id NOT IN (SELECT DISTINCT parent_id FROM category) AND l.id in " +
            "(SELECT l.id FROM language l WHERE l.code = ?) ORDER BY c.id";
    private static final String SELECT_ALL_CATEGORIES_BY_PARENT_ID_QUERY = "SELECT c.id AS category_id, cd.name " +
            "as category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name " +
            "FROM category c LEFT JOIN category_detail cd ON c.id = cd.id INNER JOIN language l ON cd.language_id = l.id " +
            "WHERE c.parent_id = ? AND l.id in (SELECT l.id FROM language l WHERE l.code = ?) ORDER BY c.id";
    private static final String INSERT_CATEGORY_QUERY = "INSERT INTO category (image, parent_id) VALUES (?,?)";
    private static final String INSERT_CATEGORY_DETAIL_QUERY = "INSERT INTO category_detail (id, language_id, name) VALUES (?,?,?)";
    private static final String UPDATE_CATEGORY_QUERY = "UPDATE category SET image=?, parent_id = ? WHERE id = ?";
    private static final String UPDATE_CATEGORY_DETAIL_QUERY = "UPDATE category_detail set name=? WHERE id = ? AND language_id = ?";
    private static final String DELETE_CATEGORY_BY_ID_QUERY = "DELETE FROM category WHERE id = ?";
    private static final String DELETE_CATEGORY_DETAILS_BY_ID_QUERY = "DELETE FROM category_detail WHERE id = ?";

    @Override
    public List<Category> getAllCategoriesAllLanguages() {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_ALL_LANGUAGES_QUERY, CATEGORIES_RESULT_SET_HANDLER);
    }

    @Override
    public List<Category> getCategoryByIdAllLanguages(int id) {
        return JdbcTemplate.select(SELECT_CATEGORY_BY_ID_ALL_LANGUAGES_QUERY, CATEGORIES_RESULT_SET_HANDLER, id);
    }

    public List<Category> getAllCategories(String language) {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_QUERY, CATEGORIES_RESULT_SET_HANDLER, language);
    }

    @Override
    public List<Category> getLeafCategories(String language) {
        return JdbcTemplate.select(SELECT_LEAF_CATEGORIES_QUERY, CATEGORIES_RESULT_SET_HANDLER, language);
    }

    @Override
    public List<Category> getAllCategoriesByParentId(int idParent, String language) {
        return JdbcTemplate.select(SELECT_ALL_CATEGORIES_BY_PARENT_ID_QUERY, CATEGORIES_RESULT_SET_HANDLER, idParent, language);
    }

    @Override
    public void saveCategory(CategoryDto categoryDto, byte[] imageBytes) {
        doInTransaction(connection -> {
            int idCategory = JdbcTemplate.executeUpdateInTransactionWithGenKeys(connection, INSERT_CATEGORY_QUERY,
                    INT_RESULT_SET_HANDLER, imageBytes, Integer.parseInt(categoryDto.getParentCategoryId()));
            categoryDto.getLangIdToCategoryName().forEach((key, value) -> JdbcTemplate.executeUpdateInTransaction(connection, INSERT_CATEGORY_DETAIL_QUERY,
                    idCategory, key, value));
        });
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, byte[] imageBytes, int id) {
        doInTransaction(connection -> {
            JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_CATEGORY_QUERY, imageBytes,
                    Integer.parseInt(categoryDto.getParentCategoryId()), id);
            categoryDto.getLangIdToCategoryName().forEach((key, value) -> JdbcTemplate.executeUpdateInTransaction(connection, UPDATE_CATEGORY_DETAIL_QUERY,
                    value, id, key));
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
