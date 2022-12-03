package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();
}
