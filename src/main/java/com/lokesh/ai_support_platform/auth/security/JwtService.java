package com.lokesh.ai_support_platform.auth.security;

import com.lokesh.ai_support_platform.auth.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    // 256-bit hexadecimal secret key secure enough for HS256 encryption
    private static final String SECRET_KEY = "9a72dd7322d7de629fbfb14e9f939e044c3bb31e5f03d21c251d18bb7201c9a6";

    // Token valid for 24 hours (in milliseconds)
    private static final long JWT_EXPIRATION = 86400000;

    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
