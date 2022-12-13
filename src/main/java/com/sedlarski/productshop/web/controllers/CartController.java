package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.view.ProductDetailsViewModel;
import com.sedlarski.productshop.domain.view.ShoppingCartItem;
import com.sedlarski.productshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public CartController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCart(String id, int quantity, HttpSession session) {
        this.initCart(session);
        ProductDetailsViewModel product = this.modelMapper.map( this.productService.findById(id),
                ProductDetailsViewModel.class);
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        this.addItemToCart(item, session);


        return super.redirect("/home");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem cartItem, HttpSession session) {
        for (ShoppingCartItem item : (List<ShoppingCartItem>) session.getAttribute("shopping-cart")) {
            if (item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        ((List<ShoppingCartItem>) session.getAttribute("shopping-cart")).add(cartItem);
    }



}
