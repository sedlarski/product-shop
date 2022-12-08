package com.sedlarski.productshop.services;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    void createOrder(String productId, String name);
}
