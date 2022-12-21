package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.models.service.OfferServiceModel;

import java.util.Arrays;
import java.util.List;

public interface OfferService {
    List<OfferServiceModel> findAllOffers();

    List<OfferServiceModel> findAllByCategory(String category);
}
