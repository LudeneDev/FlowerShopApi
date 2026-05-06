package com.ludenedev.flowershop.demo.service;


import com.ludenedev.flowershop.demo.DemoContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Profile("demo")
@Service
public class JwtService {

    private final SecretKey key;

    public JwtService() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

public String createToken(){
    String sessionId = UUID.randomUUID().toString();

    Instant now = Instant.now();
    Instant exp = Instant.now().plusSeconds(600);

    return Jwts.builder()
            .setSubject("demo-user")
            .claim("sessionId",sessionId)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .signWith(key)
            .compact();


}

public Claims parse(String token){
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
}


public String extractSessionId(String token){
    return parse(token).get("sessionId", String.class);
}
}
