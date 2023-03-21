package com.prueba.apirestful.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.prueba.apirestful.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateTokenJwt(User user){
        String tokenJwt = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10000L))
                .withJWTId(user.getUserId())
                .sign(Algorithm.HMAC256(secret));

        return tokenJwt;

    }

}
