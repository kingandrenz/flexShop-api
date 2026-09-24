package com.flexteck.flexshop.dto.request;

import jakarta.validation.constraints.NotNull;
import com.flexteck.flexshop.enums.OrderStatus;

public record UpdateOrderStatusRequest(
                @NotNull(message = "Order status is required") OrderStatus status) {
}
