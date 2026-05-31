package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * PRODUCT ENTITY — represents a row in the "products" database table.
 *
 * @Entity tells JPA: "Map this Java class to a database table."
 * @Table(name="products") names the table explicitly.
 * @Data (Lombok) auto-generates: getters, setters, toString, equals, hashCode.
 * @Id marks the primary key field.
 * @GeneratedValue auto-increments the ID.
 *
 * Each field annotated with @Column maps to a column in the table.
 * Validation annotations like @NotBlank enforce data integrity.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotBlank(message = "Category is required")
    private String category;

    private String imageUrl;

    private Double rating = 0.0;

    private Integer reviewCount = 0;

    private String badge; // e.g. "New", "Best Seller", "Sale"

    @Column(nullable = false)
    private Boolean active = true;
}
