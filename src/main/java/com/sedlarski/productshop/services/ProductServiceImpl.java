package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Category;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findById(String id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));

    }

    @Override
    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id"));
        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setCategories(productServiceModel.getCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, Category.class))
                .collect(Collectors.toList()));
        return this.modelMapper
                .map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }
}