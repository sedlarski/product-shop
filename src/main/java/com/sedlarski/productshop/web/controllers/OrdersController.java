package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.domain.rest.ProductOrderRequestModel;
import com.sedlarski.productshop.domain.view.ProductDetailsViewModel;
import com.sedlarski.productshop.services.OrderService;
import com.sedlarski.productshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrdersController extends BaseController {

    private ProductService productService;
    private ModelMapper modelMapper;

    private OrderService orderService;

    public OrdersController(ProductService productService, ModelMapper modelMapper, OrderService orderService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findById(id);
        ProductDetailsViewModel viewModel = this.modelMapper
                .map(productServiceModel, ProductDetailsViewModel.class);
        modelAndView.addObject("product", viewModel);
        return super.view("order/product", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allOrders(ModelAndView modelAndView) {
        return view("order/all-orders", modelAndView);
    }


}
