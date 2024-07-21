package me.ankanroychowdhury.scm.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.security}")
    private String jwtSecretKey;

    @Value("${security.jwt.expiration-time}")
    private Long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllPayloads(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String email){
        final String userEmailFetchedFromToken = extractUsername(token);
        return (userEmailFetchedFromToken.equals(email) && !isTokenExpired(token));
    }

    public Long getExpirationTime(){
        return jwtExpiration;
    }

    public String generateToken(String email){
        return buildToken(new HashMap<>(), email);
    }

    private String buildToken(HashMap<String, Object> payload, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration*1000L);
        return Jwts.builder()
                .addClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(getSignInKey())
                .compact();
    }

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
}
