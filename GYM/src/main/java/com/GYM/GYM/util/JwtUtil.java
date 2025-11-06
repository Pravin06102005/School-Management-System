package com.GYM.GYM.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // ✅ use a long secret key, only letters/numbers — avoid special chars
    private static final String SECRET_KEY = "mySuperSecretKeyForJwtSigningWhichIsAtLeast32CharactersLong";

    private final long EXPIRATION_TIME = 1000 * 60 * 60*24; // 1 day

    // ✅ generate a valid HMAC key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ generate token with role claim
    public String generateToken(String username, String role) {
        String baseRole = role.toUpperCase().startsWith("ROLE_")
                ? role.substring(5)
                : role;
        return Jwts.builder()
                .setSubject(username)
                .claim("role", "ROLE_" + baseRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // ✅ proper key object
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
