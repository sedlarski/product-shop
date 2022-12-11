package com.sedlarski.productshop.validation;

import com.sedlarski.productshop.domain.entities.Product;

public interface ProductValidationService {
    boolean isValid(Product product);
}
