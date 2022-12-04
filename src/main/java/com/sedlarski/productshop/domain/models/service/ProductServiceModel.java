package com.sedlarski.productshop.domain.models.service;

import com.sedlarski.productshop.domain.entities.Category;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceModel extends BaseServiceModel {
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;

    private List<Category> categories;

    public ProductServiceModel() {
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
