package com.flexteck.flexshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexteck.flexshop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
