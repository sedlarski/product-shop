package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.models.service.OrderProductServiceModel;
import com.sedlarski.productshop.domain.models.service.OrderServiceModel;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.domain.view.OrderProductViewModel;
import com.sedlarski.productshop.domain.view.ProductDetailsViewModel;
import com.sedlarski.productshop.domain.view.ShoppingCartItem;
import com.sedlarski.productshop.services.OrderService;
import com.sedlarski.productshop.services.ProductService;
import com.sedlarski.productshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final OrderService orderService;

    public CartController(ProductService productService, ModelMapper modelMapper, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCart(String id, int quantity, HttpSession session) {
        ProductDetailsViewModel product = this.modelMapper.map( this.productService.findById(id),
                ProductDetailsViewModel.class);
        OrderProductViewModel orderProductViewModel = new OrderProductViewModel();
        orderProductViewModel.setProduct(product);
        orderProductViewModel.setPrice(product.getPrice());
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(orderProductViewModel);
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
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal) {
        List<ShoppingCartItem> cart = this.retrieveCart(session);
        OrderServiceModel orderServiceModel = prepareOrder(cart, principal.getName());
        this.orderService.createOrder(orderServiceModel);
        return super.redirect("/home");
    }

    private void removeItemFromCart(String id, List<ShoppingCartItem> cart) {
        cart.removeIf(item -> item.getProduct().getProduct().getId().equals(id));
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
            if (item.getProduct().getProduct().getId().equals(cartItem.getProduct().getProduct().getId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        cart.add(cartItem);
    }

    private OrderServiceModel prepareOrder(List<ShoppingCartItem> cart, String customer) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setUser(this.userService.findByUsername(customer));
        List<OrderProductServiceModel> products = new ArrayList<>();
        for (ShoppingCartItem item : cart) {
            OrderProductServiceModel productServiceModel = this.modelMapper.map(item.getProduct(), OrderProductServiceModel.class);

            for (int i = 0; i < item.getQuantity(); i++) {
                products.add(productServiceModel);
            }

        }
        orderServiceModel.setProducts(products);
        orderServiceModel.setTotalPrice(calcTotal(cart));
        return orderServiceModel;
    }

}
