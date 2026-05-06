package com.ludenedev.flowershop.demo;

import com.ludenedev.flowershop.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Profile("demo")
@Component
@AllArgsConstructor
public class DemoJwtFilter extends OncePerRequestFilter {

    private final DemoContext context;
    private final JwtService service;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");

        if(auth != null && auth.startsWith("Bearer ")){
            String token = auth.substring(7);

            try{
                String sessionId = service.extractSessionId(token);
                context.setSessionId(sessionId);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(sessionId, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authToken);

            }catch (Exception e){

            }


        }
            try{
                filterChain.doFilter(request, response);
            }finally {
                context.clear();
            }

    }
}
