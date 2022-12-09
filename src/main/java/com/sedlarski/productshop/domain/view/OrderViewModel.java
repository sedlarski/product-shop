package com.sedlarski.productshop.domain.view;

import com.sedlarski.productshop.repository.OrderRepository;
import com.sedlarski.productshop.repository.ProductRepository;
import com.sedlarski.productshop.services.UserService;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class OrderViewModel {
    private String imageUrl;
    private String name;
    private BigDecimal price;
    private String customer;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
