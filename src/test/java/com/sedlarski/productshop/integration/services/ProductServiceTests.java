package com.sedlarski.productshop.integration.services;

import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.repository.ProductRepository;
import com.sedlarski.productshop.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @Autowired
    ProductService service;

    @MockBean
    private ProductRepository mockProductRepository;

    @Test
    public void createProduct_whenValid_productCreated() {
        service.addProduct(new ProductServiceModel());
        verify(mockProductRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void createProduct_whenNull_throw() {
        service.addProduct(null);
        verify(mockProductRepository)
                .save(any());
    }
}
