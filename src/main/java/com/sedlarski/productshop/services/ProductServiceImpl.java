package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Category;
import com.sedlarski.productshop.domain.entities.Offer;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.error.ProductNotFoundException;
import com.sedlarski.productshop.repository.CategoryRepository;
import com.sedlarski.productshop.repository.OfferRepository;
import com.sedlarski.productshop.repository.ProductRepository;
import com.sedlarski.productshop.validation.ProductValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;;
    private final ModelMapper modelMapper;

    private final ProductValidationService productValidationService;

    public ProductServiceImpl(ProductRepository productRepository, OfferRepository offerRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, ProductValidationService productValidationService) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.productValidationService = productValidationService;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        if(!productValidationService.isValid(productServiceModel)) {
            throw new IllegalArgumentException("Product is not valid");
        }
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product = this.productRepository.save(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
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
                .map(p -> {
                    ProductServiceModel productServiceModel = this.modelMapper.map(p, ProductServiceModel.class);
                    this.offerRepository.findByProduct_Id(productServiceModel.getId())
                            .ifPresent(o -> productServiceModel.setPrice(o.getPrice()));
                    return productServiceModel;
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

    }

    @Override
    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Invalid product Id"));
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

    @Override
    public List<ProductServiceModel> findAllByCategory(String category) {
        return this.productRepository
                .findAll()
                .stream()
                .filter(p -> p.getCategories()
                        .stream()
                        .anyMatch(c -> c.getName().equals(category)))
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }


}
