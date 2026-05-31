package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ENTRY POINT of the Spring Boot application.
 *
 * @SpringBootApplication is a convenience annotation that adds:
 *   - @Configuration: marks this class as a source of bean definitions
 *   - @EnableAutoConfiguration: auto-configures Spring based on dependencies
 *   - @ComponentScan: scans all classes in this package for Spring components
 *
 * When you run this class, Spring Boot starts an embedded Tomcat server
 * on port 8080 and registers all your REST API controllers automatically.
 */
@SpringBootApplication
public class LuxeStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuxeStoreApplication.class, args);
        System.out.println("\n✅ LuxeStore Backend is running at http://localhost:8080\n");
    }
}
