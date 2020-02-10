package com.swpu.sharebook.shiro.jwt;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;

public class JWTUtil {
    // 私钥
    private static final String SECRETKEY = "KeennessNewBie";

    /**
     * 生成一个jwtToken
     *
     * @param subject 用户名
     * @param expire  有效时间
     * @return json web token
     */
    public static String generatorToken(String subject, Long expire) {
        JwtBuilder jwt = Jwts.builder();
        // 设置token的唯一标识
        jwt.setId(UUID.randomUUID().toString());
        // 设置token的主体
        jwt.setSubject(subject);
        // 签发者
        jwt.setIssuer(SECRETKEY);
        // 签发时间
        jwt.setIssuedAt(new Date());
        // 设置有效时间
        if (null != expire) {
            Date expiration = new Date(System.currentTimeMillis() + expire);
            System.err.println("now" + new Date());
            System.err.println("future " + expiration);
            jwt.setExpiration(expiration);

        }
        // 私钥
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);
        // 签名
        jwt.signWith(SignatureAlgorithm.HS256, secretKeyBytes);
        return jwt.compact();
    }

    public static String parsingToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        return JSON.parseObject(body.getSubject(), String.class);
    }
}