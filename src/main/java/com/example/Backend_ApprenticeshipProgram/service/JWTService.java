package com.example.Backend_ApprenticeshipProgram.service;

import io.jsonwebtoken.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JWTService {
    private final String SECRET_KEY = "50940a1a9135bc5c44b3108e15f556f10e26359656444956bb5041bba9baac5bf78e5bb60e1ce9fc45e151fe9300762ec86fe379e940f39aa5f2b2b1234a9c4762ebb66a490af3ff4531cf7370097d0e555f571634ce31fa7d139d7b7ebee884670caab2c5d2f81ee38a8558d9ed23000d82e016eb2c0b85c0edba2eaad7aa3deb5d8cab4f3d0e6d130e43f52a1f75039e97736d577f1233b4c33bfb1f503e992d24b4927c851635e279a7dbd35a6f9a88b67673a3bd80d1dfd402f12905166ef19e63ee826f144695503ecb0b89231212fd811d6d3b6905ea2a084026004bc16bc3bb42a466ef21801406ad2940e6958e6223140a1d54ead551fb7e6382fc7b";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
    }
}
