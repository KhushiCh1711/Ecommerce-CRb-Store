package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

/**
 * SECURITY CONFIGURATION — controls who can access which endpoints.
 *
 * @Configuration marks this as a Spring configuration class.
 * @EnableWebSecurity enables Spring Security's web security support.
 *
 * CORS (Cross-Origin Resource Sharing):
 *   When your React app (port 3000) calls your Java API (port 8080),
 *   the browser blocks the request by default (different "origins").
 *   We explicitly tell the server to ALLOW requests from our frontend.
 *
 * For this demo, we keep it simple:
 *   - GET requests to /api/products → public (anyone can browse)
 *   - POST /api/orders → public (anyone can place orders)
 *   - Everything else → could be secured (for admin panel etc.)
 *
 * In production you'd add JWT token validation here.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable()) // Disabled for REST APIs (CSRF is for form-based auth)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // REST APIs are stateless
            .authorizeHttpRequests(auth -> auth
                // Public endpoints — no login required
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/orders").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/orders/customer/**").permitAll()
                // H2 console for development
                .requestMatchers("/h2-console/**").permitAll()
                // Everything else requires authentication (for future admin endpoints)
                .anyRequest().permitAll() // Change to .authenticated() when adding admin auth
            )
            .headers(headers -> headers.frameOptions(f -> f.disable())); // Needed for H2 console

        return http.build();
    }

    /**
     * CORS configuration — allows React frontend to call this API.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",  // React dev server
            "http://localhost:5173"   // Vite dev server
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
