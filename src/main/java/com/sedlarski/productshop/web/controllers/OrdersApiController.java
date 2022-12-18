package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.rest.ProductOrderRequestModel;
import com.sedlarski.productshop.services.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrdersApiController {

    private OrderService orderService;

    public OrdersApiController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/submit")
    @PreAuthorize("isAuthenticated()")
    public void submitOrder(@RequestBody ProductOrderRequestModel model, Principal principal) {
        String name = principal.getName();
//        orderService.createOrder(model.getId(), name);
    }
}
