package com.sedlarski.productshop.validation;

import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidationServiceImpl implements ProductValidationService {
    @Override
    public boolean isValid(Product product) {
        return product != null && product.getCategories() != null;
    }

    @Override
    public boolean isValid(ProductServiceModel productServiceModel) {
        return productServiceModel != null && areCategoriesValid(productServiceModel.getCategories());
    }

    private boolean areCategoriesValid(List<CategoryServiceModel> categories) {
        return categories != null
                && !categories.isEmpty();
    }
}
