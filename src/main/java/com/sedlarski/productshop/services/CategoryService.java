package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findCategoryById(String id);

    CategoryServiceModel editCategory(String id, CategoryServiceModel map);

    CategoryServiceModel deleteCategory(String id);
}
