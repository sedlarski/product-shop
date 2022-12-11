package com.sedlarski.productshop.validation;

import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;

public interface ProductValidationService {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel productServiceModel);
}
