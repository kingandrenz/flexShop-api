package com.flexteck.flexshop.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        BigDecimal totalAmount,
        String status,
        LocalDateTime createdAt,
        List<OrderItemResponse> item) {

}
