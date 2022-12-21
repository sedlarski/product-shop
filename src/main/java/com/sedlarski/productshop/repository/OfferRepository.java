package com.sedlarski.productshop.repository;

import com.sedlarski.productshop.domain.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    List<Offer> findAllByProduct_Categories(String name);
    Optional<Offer> findByProduct_Id(String id);
}
