package com.ludenedev.flowershop.configuration;

import com.ludenedev.flowershop.demo.DemoJwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    @Profile("!demo")
    @Bean
    public SecurityFilterChain setupSecurity(HttpSecurity http) {
        http.authorizeHttpRequests(
                        (authorize) -> {
                            authorize.requestMatchers("/api/flowers","/api/bouquets","/api/bills","/api/bills/**","/api/demo/**").authenticated();
                            authorize.requestMatchers("/actuator","/actuator/**").permitAll();
                            authorize.anyRequest().permitAll();

                        }
                )
                .httpBasic(Customizer.withDefaults())
                .cors(org.springframework.security.config.Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();

    }
    @Profile("!demo")
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:9000", "http://localhost:5173"));

        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Profile("demo")
    @Bean
    public SecurityFilterChain setupSecurityDemo(HttpSecurity http, DemoJwtFilter jwtAuthFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/demo/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
