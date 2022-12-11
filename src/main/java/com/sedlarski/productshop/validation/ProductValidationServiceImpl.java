package com.sedlarski.productshop.validation;

import com.sedlarski.productshop.domain.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductValidationServiceImpl implements ProductValidationService {
    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

}
