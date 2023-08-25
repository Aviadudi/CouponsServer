package com.aviad.coupons.dal;


import com.aviad.coupons.dto.Category;
import com.aviad.coupons.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoriesDal extends CrudRepository<CategoryEntity,Short> {

    @Query("SELECT new com.aviad.coupons.dto.Category (c.id, c.name) FROM CategoryEntity c")
    List<Category> getCategories();

    @Query("SELECT new com.aviad.coupons.dto.Category (c.id, c.name) FROM CategoryEntity c WHERE id = :id")
    Category getCategoryById(@Param("id") short id);
}
