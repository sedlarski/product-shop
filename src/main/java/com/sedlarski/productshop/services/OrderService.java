package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.models.service.OrderServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void createOrder(OrderServiceModel orderServiceModel);

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomer(String name);
}
