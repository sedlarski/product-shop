package com.sedlarski.productshop.repository;

import com.sedlarski.productshop.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUser_UsernameOrderByFinishedOn(String name);

}
