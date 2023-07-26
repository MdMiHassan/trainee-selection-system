package com.example.tss.service.impl;

import com.example.tss.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final Key SECRET_KEY;

    @Override
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateJwtToken(Authentication authentication) {
        final long issueTime = System.currentTimeMillis();
        return Jwts.builder()
                .addClaims(Map.of("role", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList()))
                .setSubject(((UserDetails) authentication.getPrincipal()).getUsername())
                .setIssuedAt(new Date(issueTime))
                .setExpiration(new Date(issueTime + 1000L * 60 * 60 * 24 * 30))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}