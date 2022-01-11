package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.Category;
import com.epam.aliebay.dto.CategoryDto;

import java.util.List;

public interface CategoryDao {

    List<Category> getAllCategoriesAllLanguages();

    void saveCategory(CategoryDto categoryDto, byte[] imageBytes);

    void updateCategory(CategoryDto categoryDto, byte[] imageBytes, int id);

    void deleteCategoryById(int id);

    List<Category> getAllCategoriesByParentId(int i, String en);

    List<Category> getAllCategories(String en);

    List<Category> getLeafCategories(String language);

    List<Category> getCategoryByIdAllLanguages(int id);
}