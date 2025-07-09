package com.project;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    // 自动生成足够强度的密钥
    // 用 Base64 字符串作为签名密钥，要求至少 32 字符（256-bit）长度
    private static final String SECRET = "mySuperSecretKeyThatIsLongEnough123!"; // >= 32 chars

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    /**
     * 生成JWT令牌
     */
    @Test
    public void testGenerateJwt() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");



        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, key)
                .addClaims(dataMap) //添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))  //设置过期时间
                .compact();//生成令牌

        System.out.println(jwt);

    }

    /**
     * 解析JWT令牌
     */
    @Test
    public void testParseJWT() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc1MjAyNjYxNX0.BGC_uWUD4XfJdjKPmQ585CGTh6GYYNEawyoO2c5qURQ";
        Claims claims = Jwts.parser()
                .setSigningKey(key)  //指定秘钥
                .build()
                .parseClaimsJws(token) //解析令牌
                .getBody();  //获取自定义信息

        System.out.println("解析后的 Claims:");
        System.out.println("id: " + claims.get("id"));
        System.out.println("username: " + claims.get("username"));
        System.out.println("exp: " + claims.getExpiration());
    }
}
