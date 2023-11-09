package com.back.api.Config;

import java.security.Key;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.back.api.Model.JwtTokenProvider;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
@PropertySource("classpath:application.properties")
public class JwtConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        // Gere uma chave segura usando Keys
        Key keyProvider = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return new JwtTokenProvider(keyProvider);
    }
}

