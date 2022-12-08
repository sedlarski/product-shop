package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Order;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.entities.User;
import com.sedlarski.productshop.domain.models.service.UserServiceModel;
import com.sedlarski.productshop.repository.OrderRepository;
import com.sedlarski.productshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserService userService;
    private ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Override
    public void createOrder(String productId, String name) {
        UserServiceModel userModel = userService.findByUsername(name);
        Product product = productRepository.findById(productId).orElseThrow();
        Order order = new Order();
        User user = new User();
        user.setId(userModel.getId());
        order.setUser(user);
        order.setProduct(product);

        orderRepository.save(order);

    }
}
