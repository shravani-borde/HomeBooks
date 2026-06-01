package com.example.HomeBooks.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        // Get Authorization header
        String authHeader =
                request.getHeader("Authorization");

        String token = null;

        // Check Bearer token
        if(authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            String email =
                    jwtUtil.extractEmail(token);

            System.out.println(
                    "Authenticated User: " + email
            );

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);
        }

        // Continue request
        filterChain.doFilter(request, response);
    }
}