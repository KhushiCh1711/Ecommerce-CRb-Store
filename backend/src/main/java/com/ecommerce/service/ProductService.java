package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * PRODUCT SERVICE — contains all BUSINESS LOGIC for products.
 *
 * The 3-layer architecture in Spring Boot:
 *
 *   [Controller]  →  receives HTTP requests, sends HTTP responses
 *        ↓
 *   [Service]     →  contains business rules and logic (THIS FILE)
 *        ↓
 *   [Repository]  →  talks to the database
 *
 * Why split into layers? Because:
 *   - Controllers should NOT contain business logic (hard to test, messy)
 *   - Services should NOT contain SQL (hard to change database later)
 *   - Each layer has ONE responsibility (clean, maintainable code)
 *
 * @Service marks this as a Spring service bean.
 * @RequiredArgsConstructor (Lombok) creates a constructor for all final fields,
 *   which Spring uses for "constructor injection" (the recommended way to inject deps).
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /** Get all active products */
    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    /** Get a single product by ID */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    /** Get products by category */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category);
    }

    /** Search products by keyword */
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    /** Get top rated products for homepage */
    public List<Product> getFeaturedProducts() {
        return productRepository.findTop8ByActiveTrueOrderByRatingDesc();
    }

    /** Add a new product (admin only) */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /** Update existing product (admin only) */
    public Product updateProduct(Long id, Product updated) {
        Product existing = getProductById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setCategory(updated.getCategory());
        existing.setImageUrl(updated.getImageUrl());
        existing.setBadge(updated.getBadge());
        return productRepository.save(existing);
    }

    /** Soft-delete a product (sets active = false, not actual deletion) */
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false); // soft delete — keeps order history intact
        productRepository.save(product);
    }

    /** Check if enough stock is available */
    public boolean isInStock(Long productId, int quantity) {
        Product product = getProductById(productId);
        return product.getStock() >= quantity;
    }

    /** Reduce stock after order is placed */
    public void reduceStock(Long productId, int quantity) {
        Product product = getProductById(productId);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}
