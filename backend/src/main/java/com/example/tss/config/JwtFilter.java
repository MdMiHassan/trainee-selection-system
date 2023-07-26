package com.example.tss.config;

import com.example.tss.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private String extractTokenFromRequest(HttpServletRequest serverHttpRequest) {
        String authorization = serverHttpRequest.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7);
    }

    public Authentication getAuthentication(Claims claims) {
        List<String> roles = (List<String>) claims.get("role");
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                roles.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        if (StringUtils.hasText(token)) {
            Claims claims = jwtService.parseClaims(token);
            if (claims.getExpiration().after(new Date(System.currentTimeMillis()))) {
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(claims));
            }
        }
        filterChain.doFilter(request, response);
    }
}