package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String, Object> calims= new HashMap<>();
        calims.put("id", 1);
        calims.put("username", "张三");
    //     生产jwt
        String token = JWT.create()
                .withClaim("user", calims) // 添加载荷
                .withExpiresAt(new Date((System.currentTimeMillis() + 1000 * 60 * 60* 12)))// 添加过期时间
                .sign(Algorithm.HMAC256("itheima")); //指定算法，配置密钥
        System.out.println(token);
    }
    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiend6d3p3In0sImV4cCI6MTY5OTI3ODA4MH0.xMGGm6VTOf39Qx0JQIO-vhImgvDjkuT_v5OB5o5rzE0";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token); // 验证token 生产解析后的token对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
