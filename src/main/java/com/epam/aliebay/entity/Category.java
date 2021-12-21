package com.epam.aliebay.entity;

import java.util.Objects;

public class Category {
    private int id;
    private String name;
    private String image;
    private int parentCategoryId;
    private Language language;

    public Category() {
    }

    public Category(int id, String name, String image, int parentCategoryId, Language language) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.parentCategoryId = parentCategoryId;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                ", language=" + language +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id &&
                parentCategoryId == category.parentCategoryId &&
                Objects.equals(name, category.name) &&
                Objects.equals(image, category.image) &&
                Objects.equals(language, category.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, parentCategoryId, language);
    }
}
