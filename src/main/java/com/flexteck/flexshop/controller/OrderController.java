package com.flexteck.flexshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexteck.flexshop.dto.request.CreateOrderRequest;
import com.flexteck.flexshop.dto.response.OrderResponse;
import com.flexteck.flexshop.service.OrderService;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order management", description = "Customer order operarion")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/my-orders")
    @Operation(summary = "Get all orders for the authenticated user")
    public ResponseEntity<List<OrderResponse>> getMyOrderResponseEntity() {
        List<OrderResponse> orders = orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get one of the authenticated user's orders by ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        OrderResponse response = orderService.getMyOrderById(orderId);
        return ResponseEntity.ok(response);
    }

}
