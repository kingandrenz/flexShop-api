package com.flexteck.flexshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexteck.flexshop.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameIgnoreCase(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);
}
