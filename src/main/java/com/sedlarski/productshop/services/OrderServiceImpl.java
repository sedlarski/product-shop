package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Order;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.entities.User;
import com.sedlarski.productshop.domain.models.service.OrderServiceModel;
import com.sedlarski.productshop.domain.models.service.UserServiceModel;
import com.sedlarski.productshop.repository.OrderRepository;
import com.sedlarski.productshop.repository.ProductRepository;
import com.sedlarski.productshop.validation.ProductValidationService;
import com.sedlarski.productshop.validation.UserValidationService;
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
    private UserValidationService userValidationService;
    private ProductValidationService productValidationService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductRepository productRepository, ModelMapper modelMapper, UserValidationService userValidationService, ProductValidationService productValidationService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userValidationService = userValidationService;
        this.productValidationService = productValidationService;
    }

    @Override
    public void createOrder(String productId, String name) {

        UserServiceModel userModel = userService.findByUsername(name);
        if (!userValidationService.isValid(userModel)) {
            throw new IllegalArgumentException("User is not valid");
        }

        Product product = productRepository.findById(productId)
                .filter(p -> productValidationService.isValid(p))
                .orElseThrow();
        Order order = new Order();
        User user = new User();
        user.setId(userModel.getId());
        order.setUser(user);
        order.setProduct(product);

        orderRepository.save(order);

    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());


    }

    @Override
    public List<OrderServiceModel> findOrdersByCustomer(String name) {
        return orderRepository.findAllByUser_Username(name)
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
