package com.wechat.vote.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static long time = 1000 * 60 * 60 * 72; // 有效期72小时
    private static String signature = "admin";  // 签名

    public static String createToken(String username, String role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", username)
                .claim("role", role)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();

        return jwtToken;
    }

    public static String createToken(String openid) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("openid", openid)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();

        return jwtToken;
    }

    public static boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
