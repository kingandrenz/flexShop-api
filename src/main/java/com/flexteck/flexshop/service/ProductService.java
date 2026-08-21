package com.flexteck.flexshop.service;

import com.flexteck.flexshop.dto.request.ProductRequest;
import com.flexteck.flexshop.exception.DuplicateResourceException;
import com.flexteck.flexshop.exception.ResourceNotFoundException;
import com.flexteck.flexshop.mapper.ProductMapper;
import com.flexteck.flexshop.dto.response.ProductResponse;
import com.flexteck.flexshop.entity.Category;
import com.flexteck.flexshop.entity.Product;
import com.flexteck.flexshop.repository.CategoryRepository;
import com.flexteck.flexshop.repository.ProductRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Product already exist:" + request.name());
        }

        Category category = findCategoryById(request.categoryId());

        Product product = new Product(
                request.name(),
                request.description(),
                request.price(),
                request.stockQuantity(),
                category);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        return productMapper.mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findProductById(id);

        Boolean nameChanged = !product.getName().equalsIgnoreCase(request.name());

        if (nameChanged && productRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("A product already exist" + request.name());

        }

        Category category = findCategoryById(request.categoryId());

        product.updateProduct(request.name(), request.description(), request.price(),
                request.stockQuantity(), category);

        Product savedProduct = productRepository.save(product);
        return productMapper.mapToResponse(savedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = findProductById(id);

        productRepository.delete(product);
    }

    public List<ProductResponse> getProductsByCategory(Long CategoryId) {
        Category category = findCategoryById(CategoryId);

        return productRepository.findByCategoryId(category.getId())
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

}
