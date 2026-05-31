package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ORDER ENTITY — represents a customer's purchase order.
 *
 * @OneToMany(cascade = CascadeType.ALL) means:
 *   One Order has many OrderItems.
 *   When we save/delete an Order, all its items are saved/deleted too.
 *   (Like a shopping receipt with multiple line items.)
 *
 * mappedBy = "order" tells JPA: the "order" field in OrderItem owns this relationship.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Customer details (in a real app, this would be a User foreign key)
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private String city;
    private String zipCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum OrderStatus {
        PENDING,        // Just placed
        CONFIRMED,      // Payment verified
        PROCESSING,     // Being packed
        SHIPPED,        // On the way
        DELIVERED,      // Received by customer
        CANCELLED       // Cancelled
    }
}
