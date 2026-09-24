package com.flexteck.flexshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexteck.flexshop.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUserId(Long userId);

    List<CustomerOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
}
