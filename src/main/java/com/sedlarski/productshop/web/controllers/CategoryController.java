package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.models.binding.CategoryAddBindingModel;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.domain.view.CategoryViewModel;
import com.sedlarski.productshop.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategory() {
        return super.view("category/add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(@ModelAttribute CategoryAddBindingModel model) {
        this.categoryService.addCategory(this.modelMapper.map(model, CategoryServiceModel.class));
        return super.redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        modelAndView.addObject("categories",
                this.categoryService.findAllCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                        .collect(Collectors.toList()));
        return super.view("category/all-categories", modelAndView);
    }

}
