package com.project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 自动生成足够强度的密钥
    // 用 Base64 字符串作为签名密钥，要求至少 32 字符（256-bit）长度
    private static final String SECRET = "mySuperSecretKeyThatIsLongEnough123!"; // >= 32 chars

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000; //过期时间为12小时

    /**
     * 生成JWT令牌
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     */
    public static Claims parseToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(key)  //指定秘钥
                .build()
                .parseClaimsJws(token) //解析令牌
                .getBody();  //获取自定义信息
    }
}
