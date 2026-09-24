package com.flexteck.flexshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flexteck.flexshop.dto.response.OrderItemResponse;
import com.flexteck.flexshop.dto.response.OrderResponse;
import com.flexteck.flexshop.entity.CustomerOrder;
import com.flexteck.flexshop.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "item", source = "orderItems")
    OrderResponse mapToResponse(CustomerOrder order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponse mapToItemResponse(OrderItem item);
}