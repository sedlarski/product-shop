package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.domain.view.OrderViewModel;
import com.sedlarski.productshop.domain.view.ProductDetailsViewModel;
import com.sedlarski.productshop.services.OrderService;
import com.sedlarski.productshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allOrders(ModelAndView modelAndView) {
        List<OrderViewModel> viewModels = this.orderService.findAllOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);
        return view("order/all-orders", modelAndView);
    }

    @GetMapping("/customer")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getCustomerOrders(ModelAndView modelAndView, Principal principal) {
        List<OrderViewModel> viewModels = this.orderService.findOrdersByCustomer(principal.getName())
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);
        return view("order/all-orders", modelAndView);
    }


}
