package com.aviad.coupons.dto;

import com.aviad.coupons.entities.CategoryEntity;

public class Category {
    private int id;
    private String name;


    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Category(CategoryEntity categoryEntity) {
        this.name = categoryEntity.getName();
        this.id = categoryEntity.getId();
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + id +
                ", categoryName='" + name + '\'' +
                '}';
    }
}
