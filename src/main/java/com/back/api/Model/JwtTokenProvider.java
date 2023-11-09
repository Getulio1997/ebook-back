package com.back.api.Model;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private final Key keyProvider;

    // Adicione um construtor que aceita a chave
    public JwtTokenProvider(Key keyProvider) {
        this.keyProvider = keyProvider;
    }

    public String generateToken(Usuario user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(keyProvider, SignatureAlgorithm.HS512)
                .compact();
    }
}