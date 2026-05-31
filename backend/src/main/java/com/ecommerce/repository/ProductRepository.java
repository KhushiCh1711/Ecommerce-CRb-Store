package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * PRODUCT REPOSITORY — the "database access layer" for Products.
 *
 * By extending JpaRepository<Product, Long>, Spring automatically gives us:
 *   - save(product)          → INSERT or UPDATE
 *   - findById(id)           → SELECT by primary key
 *   - findAll()              → SELECT all rows
 *   - deleteById(id)         → DELETE by primary key
 *   - count()                → COUNT(*)
 *   ...and more — ALL without writing a single SQL query!
 *
 * We can also define CUSTOM queries using:
 *   1. Method names (Spring figures out the SQL automatically)
 *   2. @Query with JPQL (Java Persistence Query Language, like SQL but for objects)
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Spring auto-generates: SELECT * FROM products WHERE active = true
    List<Product> findByActiveTrue();

    // Spring auto-generates: SELECT * FROM products WHERE category = ? AND active = true
    List<Product> findByCategoryAndActiveTrue(String category);

    // Custom JPQL query — searches name and description
    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    // Price range filter
    List<Product> findByActiveTrueAndPriceBetween(Double minPrice, Double maxPrice);

    // Top rated products
    List<Product> findTop8ByActiveTrueOrderByRatingDesc();
}
