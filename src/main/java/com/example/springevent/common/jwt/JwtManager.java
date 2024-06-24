package com.example.springevent.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtManager {

    private long accessExpiration;
    private long refreshExpiration;
    private String issuer;
    private SecretKey secretKey;

    public JwtManager(
        @Value("${secret-key}") String secretKey,
        @Value("${access-expiration-hours}") long accessExpiration,
        @Value("${refresh-expiration-hours}") long refreshExpiration,
        @Value("${issuer}") String issuer) {

        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.issuer = issuer;
    }

    public String createAccessToken(Long memberId) {
        return createToken(String.valueOf(memberId), accessExpiration);
    }

    public String createRefreshToken() {
        return createToken("", refreshExpiration);
    }

    private String createToken(String subject, long expiration) {
        Instant now = Instant.now();
        return Jwts.builder()
            .signWith(secretKey, Jwts.SIG.HS512)
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expiration, ChronoUnit.SECONDS)))
            .compact();
    }

    public String parseAccessToken(String token) {
        JwtParser jwtParser = createJwtParser();

        return parseToken(token, jwtParser)
            .getSubject();
    }

    public void validateRefreshToken(String refreshToken) {
        JwtParser jwtParser = createJwtParser();

        parseToken(refreshToken, jwtParser);
    }

    private JwtParser createJwtParser() {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build();
    }

    private Claims parseToken(String token, JwtParser jwtParser) {
        return jwtParser.parseSignedClaims(token)
            .getPayload();
    }
}
