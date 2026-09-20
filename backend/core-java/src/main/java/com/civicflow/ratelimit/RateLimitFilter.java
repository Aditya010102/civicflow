package com.civicflow.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter
        extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    public RateLimitFilter(
            RateLimitService rateLimitService
    ) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String clientKey =
                getClientKey(request);

        boolean allowed =
                rateLimitService.isAllowed(clientKey);

        if (!allowed) {

            response.setStatus(429);

            response.setHeader(
                    "Retry-After",
                    "60"
            );

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(
                    """
                    {
                      "status": 429,
                      "error": "Too Many Requests",
                      "message": "Rate limit exceeded"
                    }
                    """
            );

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }

    private String getClientKey(
            HttpServletRequest request
    ) {
        return request.getRemoteAddr();
    }
}