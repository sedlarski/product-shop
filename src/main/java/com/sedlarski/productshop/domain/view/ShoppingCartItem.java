package com.sedlarski.productshop.domain.view;

public class ShoppingCartItem {

    private ProductDetailsViewModel product;
    private int quantity;

    public ShoppingCartItem() {
    }

    public ProductDetailsViewModel getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsViewModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
