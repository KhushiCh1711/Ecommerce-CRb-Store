package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ORDER ITEM ENTITY — one line in an order (e.g. "2x Obsidian Watch @ $299").
 *
 * @ManyToOne means: Many OrderItems can belong to One Order.
 * @JoinColumn(name="order_id") creates the foreign key column in this table.
 *
 * We store price at purchase time (priceAtPurchase) so that if the product
 * price changes later, the order history stays accurate.
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    // Price locked at time of purchase
    private Double priceAtPurchase;

    public Double getSubtotal() {
        return quantity * priceAtPurchase;
    }
}
