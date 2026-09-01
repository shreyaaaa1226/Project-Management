package com.shreya.config;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMillis;

    public JwtService(@Value("${jwt.secret:}") String secret,
                      @Value("${jwt.expiration-ms:86400000}") long expirationMillis) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret is not configured. Set environment variable JWT_SECRET or property jwt.secret.");
        }
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("JWT secret is too short. Provide at least 32 bytes worth of secret.");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String jwtWithOrWithoutBearer) {
        String token = jwtWithOrWithoutBearer.startsWith("Bearer ") ? jwtWithOrWithoutBearer.substring(7) : jwtWithOrWithoutBearer;
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String jwtWithOrWithoutBearer) {
        Claims claims = getClaims(jwtWithOrWithoutBearer);
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths = new HashSet<>();
        if (collection != null) {
            for (GrantedAuthority authority : collection) {
                auths.add(authority.getAuthority());
            }
        }
        return String.join(",", auths);
    }
}
