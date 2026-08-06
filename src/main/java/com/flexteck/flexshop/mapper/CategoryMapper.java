package com.flexteck.flexshop.mapper;

import org.mapstruct.Mapper;

import com.flexteck.flexshop.dto.response.CategoryResponse;
import com.flexteck.flexshop.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse mapToResponse(Category category);
}