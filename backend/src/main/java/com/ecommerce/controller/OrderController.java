package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * ORDER CONTROLLER — REST endpoints for order management.
 *
 * Notice the inner class "OrderRequest" — this is a DTO (Data Transfer Object).
 *
 * WHY use a DTO instead of the Order model directly?
 *   - The frontend sends { customerName, email, cartItems: {1: 2, 3: 1} }
 *   - The Order model expects a different structure
 *   - DTOs act as a "translation layer" between what the client sends and
 *     what your internal model expects
 *   - They also hide internal database details from external callers (security!)
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * POST /api/orders
     * Places a new order.
     *
     * Expected JSON body:
     * {
     *   "customerName": "Aryan Sharma",
     *   "customerEmail": "aryan@email.com",
     *   "shippingAddress": "123 Main St",
     *   "city": "Mumbai",
     *   "zipCode": "400001",
     *   "cartItems": { "1": 2, "3": 1 }  // productId: quantity
     * }
     */
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
        try {
            // Build the Order object from the DTO
            Order order = new Order();
            order.setCustomerName(request.getCustomerName());
            order.setCustomerEmail(request.getCustomerEmail());
            order.setShippingAddress(request.getShippingAddress());
            order.setCity(request.getCity());
            order.setZipCode(request.getZipCode());

            Order placed = orderService.placeOrder(order, request.getCartItems());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "orderId", placed.getId(),
                    "total", placed.getTotalAmount(),
                    "status", placed.getStatus(),
                    "message", "Order placed successfully!"
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/orders/{id}
     * Returns order details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/orders/customer/{email}
     * Returns all orders for a customer.
     */
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable String email) {
        return ResponseEntity.ok(orderService.getOrdersByEmail(email));
    }

    /**
     * PATCH /api/orders/{id}/status
     * Updates order status (admin use).
     *
     * Example body: { "status": "SHIPPED" }
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            Order.OrderStatus newStatus = Order.OrderStatus.valueOf(body.get("status"));
            return ResponseEntity.ok(orderService.updateOrderStatus(id, newStatus));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid status value"));
        }
    }

    // ─────────────────────────────────────────────
    // DTO (Data Transfer Object) inner class
    // ─────────────────────────────────────────────
    @Data
    public static class OrderRequest {
        private String customerName;
        private String customerEmail;
        private String shippingAddress;
        private String city;
        private String zipCode;
        // key = productId (Long), value = quantity (Integer)
        private Map<Long, Integer> cartItems;
    }
}
