package com.flexteck.flexshop.service;

import org.springframework.stereotype.Service;
import com.flexteck.flexshop.repository.CustomerOrderRepository;
import com.flexteck.flexshop.dto.response.OrderResponse;
import com.flexteck.flexshop.exception.ResourceNotFoundException;
import com.flexteck.flexshop.mapper.OrderMapper;
import com.flexteck.flexshop.entity.CustomerOrder;
import com.flexteck.flexshop.enums.OrderStatus;
import com.flexteck.flexshop.dto.request.UpdateOrderStatusRequest;

import java.util.List;

@Service
public class AdminOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final OrderMapper orderMapper;

    public AdminOrderService(CustomerOrderRepository customerOrderRepository, OrderMapper orderMapper) {
        this.customerOrderRepository = customerOrderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderResponse> getAllOrders() {

        return customerOrderRepository.findAll()
                .stream()
                .map(orderMapper::mapToResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        CustomerOrder order = findOrder(id);
        return orderMapper.mapToResponse(order);
    }

    public OrderResponse updateStatus(
            Long id,
            UpdateOrderStatusRequest request) {

        CustomerOrder order = findOrder(id);

        validateStatusTransition(
                order.getStatus(),
                request.status());

        order.updateStatus(
                request.status());

        CustomerOrder saved = customerOrderRepository.save(order);

        return orderMapper.mapToResponse(saved);

    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {

        if (current == OrderStatus.DELIVERED) {

            throw new IllegalStateException(
                    "Delivered orders cannot be changed");

        }

        if (current == OrderStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Cancelled orders cannot be changed");

        }

        if (current == OrderStatus.PENDING) {

            if (next != OrderStatus.CONFIRMED &&
                    next != OrderStatus.CANCELLED) {

                throw new IllegalStateException(
                        "Pending order can only be confirmed or cancelled");

            }

        }

        if (current == OrderStatus.CONFIRMED) {

            if (next != OrderStatus.PROCESSING) {

                throw new IllegalStateException(
                        "Confirmed order can only move to processing");

            }

        }

        if (current == OrderStatus.PROCESSING) {

            if (next != OrderStatus.SHIPPED) {

                throw new IllegalStateException(
                        "Processing order can only move to shipped");

            }

        }

        if (current == OrderStatus.SHIPPED) {

            if (next != OrderStatus.DELIVERED) {

                throw new IllegalStateException(
                        "Shipped order can only move to delivered");

            }

        }

    }

    private CustomerOrder findOrder(Long id) {
        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

}
