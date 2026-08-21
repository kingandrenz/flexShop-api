package com.flexteck.flexshop.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.flexteck.flexshop.dto.request.CategoryRequest;
import com.flexteck.flexshop.dto.response.CategoryResponse;
import com.flexteck.flexshop.entity.Category;
import com.flexteck.flexshop.exception.DuplicateResourceException;
import com.flexteck.flexshop.exception.ResourceNotFoundException;
import com.flexteck.flexshop.mapper.CategoryMapper;
import com.flexteck.flexshop.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Category already exists: " + request.name());
        }

        Category category = new Category(
                request.name(),
                request.description());

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return categoryMapper.mapToResponse(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = findCategoryById(id);

        category.updateCategory(
                request.name(), request.description());

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(savedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with the id " + id));
    }
}
