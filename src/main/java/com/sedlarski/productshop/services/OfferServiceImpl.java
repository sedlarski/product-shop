package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Offer;
import com.sedlarski.productshop.domain.entities.Product;
import com.sedlarski.productshop.domain.models.service.OfferServiceModel;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.repository.OfferRepository;
import com.sedlarski.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ProductRepository productRepository, ProductService productService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferServiceModel> findAllByCategory(String category) {
        return offerRepository.findAllByProduct_Categories(category)
                .stream()
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 10000)
    private void generateOffers() {
        this.offerRepository.deleteAll();
        List<ProductServiceModel> products = this.productService.findAllProducts();
        List<Offer> offers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Offer offer = new Offer();
            offer.setProduct(this.modelMapper.map(products.get(random.nextInt(products.size())), Product.class));
            offer.setPrice(offer.getProduct().getPrice().multiply(new BigDecimal(0.8)));
            if(offers.stream().filter(o -> o.getProduct().getId().equals(offer.getProduct().getId())).count() == 0) {
                offers.add(offer);
            }

        }

        this.offerRepository.saveAll(offers);
    }
}
