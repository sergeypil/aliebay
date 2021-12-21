package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryDao {

    List<Category> getAllCategoriesAllLanguages();

    Optional<Category> getCategoryById(int id, String language);

    void saveCategory(Map<Integer, Category> langToCategory);

    void updateCategory(Map<Integer, Category> langToCategory, int id);

    void deleteCategoryById(int id);

    List<Category> getAllCategoriesByParentId(int i, String en);

    List<Category> getAllCategories(String en);

    List<Category> getLeafCategories(String language);

    List<Category> getCategoryByIdAllLanguages(int id);
}