package com.ecommerce.config;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * DATA SEEDER — pre-populates the database with sample products on startup.
 *
 * CommandLineRunner is a Spring Boot interface.
 * Its run() method is called automatically when the application starts.
 *
 * This is useful for:
 *   - Development: having test data ready immediately
 *   - Demo: showing the app works without manual data entry
 *   - Testing: consistent baseline data
 *
 * In production, you'd remove this or guard it with a check like:
 *   if (productRepository.count() == 0) { ... seed data ... }
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if the database is empty
        if (productRepository.count() > 0) {
            System.out.println("📦 Database already has products. Skipping seed.");
            return;
        }

        List<Product> products = List.of(
            createProduct("Obsidian Chronograph", "Precision movement, sapphire crystal glass, 100m water resistant.",
                299.00, 45, "Watches", "⌚", 4.8, 124, "Best Seller"),
            createProduct("Lumina Wireless Buds", "Active noise cancellation, 30hr battery life, spatial audio.",
                149.00, 78, "Audio", "🎧", 4.7, 89, "New"),
            createProduct("Apex Carbon Sneakers", "Carbon fiber sole, breathable mesh, ergonomic arch support.",
                189.00, 32, "Footwear", "👟", 4.6, 203, "Hot"),
            createProduct("Nova Smart Backpack", "USB charging port, anti-theft zippers, 30L capacity.",
                129.00, 55, "Bags", "🎒", 4.5, 67, null),
            createProduct("Drift Mechanical Keyboard", "Cherry MX switches, aluminum frame, RGB backlit keys.",
                219.00, 28, "Tech", "⌨️", 4.9, 312, "Top Rated"),
            createProduct("Velvet Leather Wallet", "Full grain leather, RFID blocking, ultra slim 6mm profile.",
                79.00, 120, "Accessories", "👛", 4.4, 45, null),
            createProduct("Zenith Desk Lamp", "3000K-6500K color temp, wireless charging base, touch control.",
                99.00, 60, "Home", "💡", 4.6, 88, "Sale"),
            createProduct("Vertex Sport Watch", "GPS tracking, heart rate monitor, 7-day battery life.",
                179.00, 40, "Watches", "⏱️", 4.5, 156, null),
            createProduct("Prism Sunglasses", "Polarized UV400 lenses, titanium frame, spring hinges.",
                119.00, 85, "Accessories", "🕶️", 4.7, 72, "New")
        );

        productRepository.saveAll(products);
        System.out.println("✅ Seeded " + products.size() + " products into the database.");
    }

    private Product createProduct(String name, String desc, Double price, Integer stock,
                                   String category, String imageUrl, Double rating,
                                   Integer reviews, String badge) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setCategory(category);
        p.setImageUrl(imageUrl);
        p.setRating(rating);
        p.setReviewCount(reviews);
        p.setBadge(badge);
        p.setActive(true);
        return p;
    }
}
