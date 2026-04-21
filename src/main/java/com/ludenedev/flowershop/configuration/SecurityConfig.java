package com.ludenedev.flowershop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain setupSecurity(HttpSecurity http) {
        http.authorizeHttpRequests(
                        (authorize) -> {
                            authorize.requestMatchers("/flowers","/bouquets","/bills","/bills/**").authenticated();
                            authorize.requestMatchers("/actuator","/actuator/**").permitAll();


                        }
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


}
