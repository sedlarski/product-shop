package com.sedlarski.productshop.unit.validation;

import com.sedlarski.productshop.domain.entities.Category;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.validation.ProductValidationService;
import com.sedlarski.productshop.validation.ProductValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductValidationServiceTests {

    private ProductValidationService productValidationService;

    @Before
    public void setUpTest() {
        this.productValidationService = new ProductValidationServiceImpl();
    }

    @Test
    public void isValid_whenNull_false() {
        Product product = null;
        boolean result = productValidationService.isValid(product);
        assertFalse(result);
    }

    @Test
    public void isValid_whenValid_true() {
        Product product = new Product();
        product.setCategories(List.of(new Category()));

        boolean result = productValidationService.isValid(product);
        assertTrue(result);
    }

    @Test
    public void t1() {
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(new ArrayList<>());

        boolean result = productValidationService.isValid(product);
        assertFalse(result);
    }

    @Test
    public void t2() {
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(new ArrayList<>());

        boolean result = productValidationService.isValid(product);
        assertFalse(result);
    }
}
