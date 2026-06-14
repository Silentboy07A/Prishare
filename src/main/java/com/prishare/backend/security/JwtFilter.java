package com.prishare.backend.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        String uri = req.getRequestURI();

        if (uri.equals("/login") ||
            uri.equals("/register")) {

            chain.doFilter(request, response);
            return;
        }

        String authHeader =
                req.getHeader("Authorization");

        if (authHeader == null ||
            !authHeader.startsWith("Bearer ")) {

            ((HttpServletResponse) response)
                    .sendError(401, "Missing Token");
            return;
        }

        String token = authHeader.substring(7);

        try {

            JwtUtil.validateToken(token);

            chain.doFilter(request, response);

        } catch (Exception e) {

            ((HttpServletResponse) response)
                    .sendError(401, "Invalid Token");
        }
    }
}