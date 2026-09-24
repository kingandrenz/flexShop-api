package com.flexteck.flexshop.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record CreateOrderRequest(
        @NotEmpty(message = "Order must contain at least 1 orderItem") List<@Valid OrderItemRequest> items) {

}
