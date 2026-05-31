package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ORDER SERVICE — handles order placement and management.
 *
 * @Transactional is very important here!
 * It wraps the entire placeOrder() method in a database transaction.
 *
 * This means: if ANY step fails (e.g. stock reduction for item 3),
 * ALL previous steps are automatically ROLLED BACK (undone).
 *
 * Example: if you order 3 products and product 2 is out of stock,
 * the already-reduced stock of product 1 is automatically restored.
 * This prevents corrupt/partial data in your database.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    /**
     * Place a new order.
     * @param orderRequest — order info (customer + items)
     * @param cartItems    — map of productId → quantity
     */
    @Transactional  // ensures all-or-nothing: either everything saves, or nothing does
    public Order placeOrder(Order orderRequest, Map<Long, Integer> cartItems) {

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        // Step 1: Validate stock and prepare order items
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productService.getProductById(productId);

            // Check stock availability
            if (!productService.isInStock(productId, quantity)) {
                throw new RuntimeException("Item out of stock: " + product.getName());
            }

            // Create order item
            OrderItem item = new OrderItem();
            item.setOrder(orderRequest);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPriceAtPurchase(product.getPrice()); // lock current price
            orderItems.add(item);

            total += product.getPrice() * quantity;
        }

        // Step 2: Set order details
        orderRequest.setItems(orderItems);
        orderRequest.setTotalAmount(total);
        orderRequest.setStatus(Order.OrderStatus.CONFIRMED);

        // Step 3: Save the order (cascades to OrderItems automatically)
        Order savedOrder = orderRepository.save(orderRequest);

        // Step 4: Reduce stock for each item
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            productService.reduceStock(entry.getKey(), entry.getValue());
        }

        return savedOrder;
    }

    /** Get order details by ID */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    /** Get all orders for a customer */
    public List<Order> getOrdersByEmail(String email) {
        return orderRepository.findByCustomerEmailOrderByCreatedAtDesc(email);
    }

    /** Update order status (admin use) */
    public Order updateOrderStatus(Long id, Order.OrderStatus newStatus) {
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /** Get all orders (admin dashboard) */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
