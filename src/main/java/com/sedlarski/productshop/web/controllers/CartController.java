package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.view.ProductDetailsViewModel;
import com.sedlarski.productshop.domain.view.ShoppingCartItem;
import com.sedlarski.productshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
        ProductDetailsViewModel product = this.modelMapper.map( this.productService.findById(id),
                ProductDetailsViewModel.class);
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(product);
        item.setQuantity(quantity);

        List<ShoppingCartItem> cart = this.retrieveCart(session);

        this.addItemToCart(item, cart);


        return super.redirect("/home");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {
        List<ShoppingCartItem> cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice",
                calcTotal(cart));
        return super.view("cart/cart-details", modelAndView);
    }
    
    @PostMapping("/remove-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeProduct(String id, HttpSession session) {

        List<ShoppingCartItem> cart = this.retrieveCart(session);
        removeItemFromCart(id, cart);
        return super.redirect("/cart/details");
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkoutConfirm(ModelAndView modelAndView, HttpSession session) {
        List<ShoppingCartItem> cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice",
                calcTotal(cart));
        return super.redirect("/home");
    }

    private void removeItemFromCart(String id, List<ShoppingCartItem> cart) {
        cart.removeIf(item -> item.getProduct().getId().equals(id));
    }

    private List<ShoppingCartItem> retrieveCart(HttpSession session) {

        if( session.getAttribute("shopping-cart") == null) {
            initCart(session);
        }
        return (List<ShoppingCartItem>) session.getAttribute("shopping-cart");
    }

    private BigDecimal calcTotal(List<ShoppingCartItem> cart) {
        BigDecimal total = cart.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;

    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem cartItem, List<ShoppingCartItem> cart) {
        for (ShoppingCartItem item : cart) {
            if (item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        cart.add(cartItem);
    }



}
