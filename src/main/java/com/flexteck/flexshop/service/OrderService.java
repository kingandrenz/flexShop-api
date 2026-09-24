package com.flexteck.flexshop.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flexteck.flexshop.dto.request.CreateOrderRequest;
import com.flexteck.flexshop.dto.response.OrderResponse;
import com.flexteck.flexshop.entity.AppUser;
import com.flexteck.flexshop.entity.CustomerOrder;
import com.flexteck.flexshop.entity.Product;
import com.flexteck.flexshop.exception.ResourceNotFoundException;
import com.flexteck.flexshop.mapper.OrderMapper;
import com.flexteck.flexshop.repository.AppUserRepository;
import com.flexteck.flexshop.repository.CustomerOrderRepository;
import com.flexteck.flexshop.repository.ProductRepository;
import com.flexteck.flexshop.entity.OrderItem;
import com.flexteck.flexshop.dto.request.OrderItemRequest;

@Service
@Transactional
public class OrderService {
        private final CustomerOrderRepository orderRepository;
        private final ProductRepository productRepository;
        private final AppUserRepository userRepository;
        private final OrderMapper orderMapper;

        public OrderService(CustomerOrderRepository orderRepository, ProductRepository productRepository,
                        AppUserRepository userRepository, OrderMapper orderMapper) {
                this.orderRepository = orderRepository;
                this.productRepository = productRepository;
                this.userRepository = userRepository;
                this.orderMapper = orderMapper;
        }

        public OrderResponse createOrder(CreateOrderRequest request) {
                String email = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

                AppUser user = userRepository.findByEmailIgnoreCase(email)
                                .orElseThrow(
                                                () -> new ResourceNotFoundException("User not found"));

                CustomerOrder order = new CustomerOrder(user);

                for (OrderItemRequest itemRequest : request.items()) {

                        Product product = productRepository.findById(itemRequest.productId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                        OrderItem item = new OrderItem(
                                        product,
                                        itemRequest.quantity());
                        order.addItem(item);
                }

                order.calculateTotal();

                CustomerOrder savedOrder = orderRepository.save(order);

                return orderMapper.mapToResponse(savedOrder);

        }

        public List<OrderResponse> getMyOrders() {
                String email = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

                AppUser user = userRepository.findByEmailIgnoreCase(email)
                                .orElseThrow(
                                                () -> new ResourceNotFoundException("User not found"));
                return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                                .stream()
                                .map(orderMapper::mapToResponse)
                                .toList();
        }

        public OrderResponse getMyOrderById(Long orderId) {
                String email = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

                AppUser user = userRepository.findByEmailIgnoreCase(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                CustomerOrder order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

                if (!order.getUser().getId().equals(user.getId())) {
                        throw new ResourceNotFoundException("Order not found for this user");
                }
                return orderMapper.mapToResponse(order);
        }
}
