package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Category;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);
        this.categoryRepository.saveAndFlush(category);
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll().stream()
                .map(c -> this.modelMapper
                        .map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryById(String id) {
        CategoryServiceModel categoryServiceModel = modelMapper
                .map(this.categoryRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category Id")), CategoryServiceModel.class);
        return categoryServiceModel;

    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id"));
        category.setName(categoryServiceModel.getName());
        return this.modelMapper
                .map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel deleteCategory(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id"));
        this.categoryRepository.delete(category);
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }
}
