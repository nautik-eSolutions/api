package com.nautik.api.service.jwt;

import com.nautik.api.domain.Token;
import com.nautik.api.domain.users.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private long expiration = 3600;
    private String secretKey = "";

    public Token generateToken(User user){
        return new Token(
                Jwts.builder().setSubject(user.getEmail())
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
}
