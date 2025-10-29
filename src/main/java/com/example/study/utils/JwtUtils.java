package com.example.study.utils;

import com.example.study.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfig jwtConfig;
    private SecretKey key;  // 초기화는 @PostConstruct에서

    /* jjwt 0.11.+ 부턴 String 키 X */
    /* 아래 공식 예제에서도 SecretKey + Base64 디코딩 사용 */
    /* https://github.com/spring-projects/spring-security-samples */
    /* https://www.baeldung.com/spring-security-oauth-jwt */
    @PostConstruct
    public void init() {
        // jwtConfig이 주입된 후 실행 → 안전!
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }



    public String createToken(Long userId, String username, List<String> roles, long expirationMs) {
        var now = System.currentTimeMillis();

        var builder = Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMs))
                .signWith(key);

        return builder.compact();
    }

    public String generateAccessToken(Long userId, String username, List<String> roles) {
        return createToken(userId, username, roles, jwtConfig.getExpirationMs());
    }

    public String generateRefreshToken(Long userId, String username) {
        return createToken(userId, username, null, jwtConfig.getRefreshExpirationMs());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

}
