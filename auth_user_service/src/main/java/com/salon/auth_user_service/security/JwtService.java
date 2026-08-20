package com.salon.auth_user_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final SecureRandom secureRandom;
    private final long refreshTokenExpiration;

    public JwtService(@Value("${jwt.secret}") String secret
            , @Value("${jwt.access-token-expiration}") long accessTokenExpiration
                      ,@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
                      , SecureRandom secureRandom
    ){
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.secureRandom = secureRandom;
    }

    public String generateAccessToken(UserDetails userDetails){
        Date isuueAt = new Date();
        Date expiration = new Date(isuueAt.getTime() + accessTokenExpiration);

        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replaceFirst("ROLE_", ""))
                .orElse(null);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role",role)
                .issuedAt(isuueAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public LocalDateTime getRefreshTokenExpiration() {
        return  LocalDateTime.now().plus(refreshTokenExpiration, ChronoUnit.MILLIS);
    }

    public String generateRefreshToken(){
        byte[] randomBytes = new byte[64];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public String hashRefreshToken(String refreshToken) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            refreshToken.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(
                        String.format("%02x", b)
                );
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 algorithm not available", e
            );
        }
    }

    public String extractUsername(String jwtToken){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(
            String jwt,
            UserDetails userDetails
    ) {

        try {

            String username = extractUsername(jwt);

            return username.equals(userDetails.getUsername())
                    && !isTokenExpired(jwt);

        } catch (Exception e) {

            return false;
        }
    }

    private boolean isTokenExpired(String jwt) {

        return extractExpiration(jwt)
                .before(new Date());
    }

    private Date extractExpiration(String jwt) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration();
    }
}
