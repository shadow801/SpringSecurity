package com.yewen.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ShadowStart
 * @create 2021-07-17 22:11
 */
@Component
public class TokenManager {

    // token有效时长
    private long tokenExpiration = 24 * 60 * 60 * 1000;
    // 编写秘钥
    private String tokenSingKey = "123456";

    // 1、根据用户名生产token
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() +tokenExpiration)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, tokenSingKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    // 2、根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        String userInfo = Jwts.parser().setSigningKey(tokenSingKey).parseClaimsJws(token).getBody().getSubject();
        return userInfo;
    }

    // 3、删除token
    public void removeToken(String token) {

    }
}
