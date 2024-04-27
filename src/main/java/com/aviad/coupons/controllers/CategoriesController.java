package com.aviad.coupons.controllers;

import com.aviad.coupons.dto.Category;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.logic.CategoryLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoryLogic categoryLogic;

    @Autowired
    public CategoriesController(CategoryLogic categoryLogic) {
        this.categoryLogic = categoryLogic;
    }

    @PostMapping
    public void createCategory(@RequestBody Category category) throws ApplicationException {
        this.categoryLogic.addCategory(category);
    }

    @PutMapping
    public void updateCategory(@RequestBody Category category) throws ApplicationException {
        this.categoryLogic.updateCategory(category);
    }

    @GetMapping
    public List<Category> getCategories() throws ApplicationException {
        return this.categoryLogic.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") int id) throws ApplicationException {
        return this.categoryLogic.getCategory(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") int id) throws ApplicationException {
        this.categoryLogic.deleteCategory(id);
    }
}
