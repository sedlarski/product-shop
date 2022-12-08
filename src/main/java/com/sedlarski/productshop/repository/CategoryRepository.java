package com.sedlarski.productshop.repository;

import com.sedlarski.productshop.domain.entities.Category;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<ProductServiceModel> findAllByName(String category);
}
