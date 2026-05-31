package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * PRODUCT CONTROLLER — exposes REST API endpoints for products.
 *
 * @RestController = @Controller + @ResponseBody
 *   Means: this class handles HTTP requests and returns JSON responses.
 *
 * @RequestMapping("/api/products") — all endpoints in this class start with /api/products
 *
 * @CrossOrigin(origins = "*") — allows the React frontend (on port 3000)
 *   to call this API (on port 8080) without CORS errors.
 *
 * HTTP Methods and their meanings:
 *   GET    → Read data (list products, get one product)
 *   POST   → Create new data (add a product)
 *   PUT    → Update existing data (edit a product)
 *   DELETE → Remove data (delete a product)
 *
 * ResponseEntity<T> lets us control the HTTP status code returned:
 *   200 OK, 201 Created, 404 Not Found, 400 Bad Request, etc.
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * GET /api/products
     * Returns all active products.
     * Optional query params: category, search
     *
     * Examples:
     *   GET /api/products              → all products
     *   GET /api/products?category=Watches  → filtered by category
     *   GET /api/products?search=apple      → searched by keyword
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {

        List<Product> products;

        if (search != null && !search.isEmpty()) {
            products = productService.searchProducts(search);
        } else if (category != null && !category.isEmpty()) {
            products = productService.getProductsByCategory(category);
        } else {
            products = productService.getAllProducts();
        }

        return ResponseEntity.ok(products); // 200 OK with product list as JSON
    }

    /**
     * GET /api/products/featured
     * Returns top rated products for the homepage.
     */
    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        return ResponseEntity.ok(productService.getFeaturedProducts());
    }

    /**
     * GET /api/products/{id}
     * Returns a single product by ID.
     * {id} is a "path variable" — e.g. /api/products/5 → id = 5
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * POST /api/products
     * Creates a new product. Requires admin role in production.
     * @Valid triggers all validation annotations on the Product model.
     * @RequestBody reads the JSON body and converts it to a Product object.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
    }

    /**
     * PUT /api/products/{id}
     * Updates an existing product.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/products/{id}
     * Soft-deletes a product (sets active = false).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
