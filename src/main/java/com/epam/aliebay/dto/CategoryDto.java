package com.epam.aliebay.dto;

import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

public class CategoryDto {
    private Part imagePart;
    private String parentCategoryId;
    private Map<Integer, String> langIdToCategoryName = new HashMap<>();

    public Part getImagePart() {
        return imagePart;
    }

    public void setImagePart(Part imagePart) {
        this.imagePart = imagePart;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Map<Integer, String> getLangIdToCategoryName() {
        return langIdToCategoryName;
    }

    public void setLangIdToCategoryName(Map<Integer, String> langIdToCategoryName) {
        this.langIdToCategoryName = langIdToCategoryName;
    }
}
