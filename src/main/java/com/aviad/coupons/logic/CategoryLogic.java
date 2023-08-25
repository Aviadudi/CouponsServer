package com.aviad.coupons.logic;


import com.aviad.coupons.dal.ICategoriesDal;
import com.aviad.coupons.dto.Category;
import com.aviad.coupons.entities.CategoryEntity;
import com.aviad.coupons.enums.ErrorType;
import com.aviad.coupons.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryLogic {
    ICategoriesDal categoriesDal;

    public CategoryLogic(ICategoriesDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public Short addCategory(Category category) throws ApplicationException {
        validateCategory(category);
        CategoryEntity categoryEntity = new CategoryEntity(category);
        this.categoriesDal.save(categoryEntity);
        category.setId(categoryEntity.getId());
        return category.getId();
    }

    public Category getCategory(short id) throws ApplicationException {
        return this.categoriesDal.getCategoryById(id);
    }

    public void deleteCategory(short id) {
        this.categoriesDal.deleteById(id);
    }

    public void updateCategory(Category category) throws ApplicationException {
        validateCategory(category);
        CategoryEntity categoryEntity = new CategoryEntity(category);
        this.categoriesDal.save(categoryEntity);
    }

    public List<Category> getAllCategories() throws ApplicationException {
        return this.categoriesDal.getCategories();
    }

    private void validateCategory(Category category) throws ApplicationException {
        validateCategoryName(category.getName());
    }

    private void validateCategoryName(String categoryName) throws ApplicationException {
        if (categoryName.length() < 3) {
            throw new ApplicationException(ErrorType.CATEGORY_NAME_TOO_SHORT);
        }
        if (categoryName.length() > 20) {
            throw new ApplicationException(ErrorType.CATEGORY_NAME_TOO_LONG);
        }
    }
}
