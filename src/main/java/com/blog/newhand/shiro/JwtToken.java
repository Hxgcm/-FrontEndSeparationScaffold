package com.blog.newhand.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author HeXianGang
 * @description JwtToken
 * @create 2021-09-02 22:56
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt) {
        this.token = jwt;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
