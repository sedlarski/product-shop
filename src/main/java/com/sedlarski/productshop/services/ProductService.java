package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.models.service.ProductServiceModel;

import java.util.Arrays;
import java.util.List;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findById(String id);

    void deleteProduct(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllByCategory(String category);
}
