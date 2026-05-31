package com.ecommerce.repository;

import com.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ORDER REPOSITORY — database access for Orders.
 *
 * Notice how simple this is! Spring Data JPA handles all the SQL.
 * We just declare what we want using method names or @Query.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders for a customer by email
    List<Order> findByCustomerEmailOrderByCreatedAtDesc(String email);

    // Find orders by status
    List<Order> findByStatus(Order.OrderStatus status);
}
