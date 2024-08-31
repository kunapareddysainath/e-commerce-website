package com.commerce.student.security;

import com.commerce.student.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Map<String, String> claimsMap = new HashMap<>(1);
        claimsMap.put(Claims.SUBJECT, userPrincipal.getUsername());

        Key key = Keys.hmacShaKeyFor((jwtSecret).getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .setClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String refreshToken(User user) {

        if (user == null) throw new RuntimeException();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Map<String, String> claimsMap = new HashMap<>(1);
        claimsMap.put(Claims.SUBJECT, user.getUsername());

        Key key = Keys.hmacShaKeyFor((jwtSecret).getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getId())
                .setClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey((jwtSecret).getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(authToken);
        } catch (MalformedJwtException | ExpiredJwtException
                 | UnsupportedJwtException | IllegalArgumentException
                 | SignatureException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey((jwtSecret).getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
