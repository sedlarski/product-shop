package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Order;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.entities.User;
import com.sedlarski.productshop.domain.models.service.OrderServiceModel;
import com.sedlarski.productshop.domain.models.service.UserServiceModel;
import com.sedlarski.productshop.repository.OrderRepository;
import com.sedlarski.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserService userService;
    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<OrderServiceModel> findAllOrders() {
        var models =  orderRepository.findAll()
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());

        return models;

    }
}
