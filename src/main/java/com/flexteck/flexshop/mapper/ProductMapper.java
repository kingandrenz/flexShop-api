package com.flexteck.flexshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flexteck.flexshop.dto.response.ProductResponse;
import com.flexteck.flexshop.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductResponse mapToResponse(Product product);

}
