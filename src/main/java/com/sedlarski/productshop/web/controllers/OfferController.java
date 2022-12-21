package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.view.OfferViewModel;
import com.sedlarski.productshop.domain.view.ProductAllViewModel;
import com.sedlarski.productshop.services.OfferService;
import com.sedlarski.productshop.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OfferController extends BaseController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/top-offers")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView topOffers(ModelAndView modelAndView) {

        return super.view("offer/top-offers", modelAndView);
    }

    @GetMapping("/top-offers/{category}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<OfferViewModel> fetchByCategory(@PathVariable String category) {
        List<OfferViewModel> topOffers = new ArrayList<>();
        if(category.equals("all")) {
            return this.offerService.findAllOffers()
                    .stream()
                    .map(p -> this.modelMapper.map(p, OfferViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.offerService.findAllByCategory(category)
                .stream()
                .map(p -> this.modelMapper.map(p, OfferViewModel.class))
                .collect(Collectors.toList());
    }
}


