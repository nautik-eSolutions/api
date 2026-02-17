package com.nautik.api.service.jwt;

import com.nautik.api.domain.Token;
import com.nautik.api.domain.users.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private long expiration = 36000 * 1000;
    private String secretKey = "ygE1zo0GTZDa7JGtzstqmF2LxivGcgho3CzLSPY0GHI";

    public Token generateToken(User user){
        return new Token(
                Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("userName", user.getUserName())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(getSecretKey())
                        .compact()
        );

    }


    public Token generateWorkerToken(User user){
        return new Token(
                Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("userName", user.getUserName())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(getSecretKey())
                        .compact()
        );
    }


    public SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody().get("userName", String.class);
    }



}
