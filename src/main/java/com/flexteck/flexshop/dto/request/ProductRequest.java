package com.flexteck.flexshop.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "Product name is required") @Size(min = 2, max = 150, message = "product name must be between 2 and 150 characters") String name,

        @NotBlank(message = "Product description is required") @Size(min = 10, max = 2000, message = "product description must be between 10 and 2000 characters") String description,

        @NotNull(message = "Product price is required") @DecimalMin(value = "0.01", message = "Product price must be greater than zero") BigDecimal price,

        @NotNull(message = "Product stock quantity is required") @Min(value = 0, message = "Stock Quantity can not be negative") Integer stockQuantity,
        @NotNull(message = "Category ID is required") Long categoryId) {
}
