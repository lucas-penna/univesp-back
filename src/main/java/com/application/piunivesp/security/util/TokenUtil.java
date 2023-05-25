package com.application.piunivesp.security.util;

import com.application.piunivesp.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenUtil {

    @Value(value = "${jwt.expiration}")
    public static final int EXPIRATION_TOKEN = 720;

    @Value(value = "${jwt.secret}")
    public static final String SECRET = "cb6982a8-bfbe-4d07-bd61-baf7ab77a82f";

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN * 60000))
                .sign(Algorithm.HMAC512(SECRET));
    }

    public String generateRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN * 60000))
                .sign(Algorithm.HMAC512(SECRET));
    }

    public String getEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token)
                .getSubject();
    }

}
