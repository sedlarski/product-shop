package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Category;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
