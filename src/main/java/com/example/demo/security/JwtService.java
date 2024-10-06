package com.example.demo.security;

import com.example.demo.dto.RegisterDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${secret_key}")
    private final static String SECRET = "6357A87FD02D14A76FF7997AF8B9EE37598C42D74883AC05416A6A166AA3C4DD619F77C343EC0E0375FCD20A7D21CCAE9F078795E50B479264B99DC332AA2160";
    private final static Long VALIDITY = 3000000L;

    // token generator
    public String generateToken(RegisterDto registerDto) {
        // if you need to add claims
//        Map<String,String> claims = new HashMap<>();
//        claims.put("name", registerDto.getFirstName());
        return Jwts.builder()
//                .claims(claims)
                .subject(registerDto.getEmail())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    // secret key generator
    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }


    // extract username from the token
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }


    // extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // validate the token
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isExpired(token));
    }

    // check if the token expired
    private boolean isExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }



    // extract token expiration date
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
