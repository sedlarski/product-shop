package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.models.binding.ProductAddBindingModel;
import com.sedlarski.productshop.domain.models.service.CloudinaryService;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.domain.view.ProductAllViewModel;
import com.sedlarski.productshop.domain.view.ProductDetailsViewModel;
import com.sedlarski.productshop.error.ProductNotFoundException;
import com.sedlarski.productshop.services.CategoryService;
import com.sedlarski.productshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;

    public ProductController(ProductService productService, ModelMapper modelMapper, CategoryService categoryService, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct() {
        return super.view("product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                .stream()
                .filter(c -> model.getCategories().contains(c.getId()))
                .collect(Collectors.toList()));
        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.addProduct(productServiceModel);
        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList()));
        return super.view("product/all-products", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductDetailsViewModel productDetailsViewModel =
                this.modelMapper.map(this.productService.findById(id), ProductDetailsViewModel.class);
        modelAndView.addObject("product", productDetailsViewModel);
        return super.view("product/details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findById(id);
        ProductAddBindingModel model =
                this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);

        model.setCategories(productServiceModel.getCategories()
                .stream()
                .map(c -> c.getName())
                .collect(Collectors.toList()));
        modelAndView.addObject("product", model);
        return super.view("product/edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute ProductAddBindingModel model) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(this.categoryService.findAllCategories()
                .stream()
                .filter(c -> model.getCategories().contains(c.getId()))
                .collect(Collectors.toList()));
        this.productService.editProduct(id, productServiceModel);
        return super.redirect("/products/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories()
                .stream()
                .map(c -> c.getName())
                .collect(Collectors.toList()));
        modelAndView.addObject("product", model);
        return super.view("/product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.productService.deleteProduct(id);
        return super.redirect("/products/all");
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<ProductAllViewModel> fetchByCategory(@PathVariable String category) {
        if(category.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findAllByCategory(category)
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());
    }


}
