package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
