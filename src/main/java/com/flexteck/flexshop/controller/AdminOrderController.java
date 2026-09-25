package com.flexteck.flexshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flexteck.flexshop.dto.response.OrderResponse;
import com.flexteck.flexshop.dto.request.UpdateOrderStatusRequest;
import com.flexteck.flexshop.service.AdminOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/orders")
@Tag(name = "Admin Order management", description = "Admin order operations for managing customers orders")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @GetMapping
    @Operation(summary = "get all orders for the authenticated user")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(adminOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "get order by ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(adminOrderService.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "update order status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(adminOrderService.updateStatus(id, request));
    }
}
