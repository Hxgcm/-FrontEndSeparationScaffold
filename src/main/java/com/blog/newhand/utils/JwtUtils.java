package com.blog.newhand.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author HeXianGang
 * @description JwtUtil 生成和校验jwt的工具类
 * @create 2021-09-03 20:37
 */
@Component
@ConfigurationProperties(prefix = "newhand.jwt")
public class JwtUtils {
    /**
     * 加密秘钥：自己生成
     */
    private String secret;
    /**
     * token有效时长
     */
    private long expire;
    /**
     * 头部信息
     */
    private String header;

    /**
     * 生成 jwt  Token
     * @param userId
     * @return
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();

        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 验证token是否正确
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("validate is token error " +  e);
            return null;
        }
    }

    /**
     * 判断token是否过期
     * @param expiration
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
