package com.swpu.sharebook.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author KeennessNewBie
 * @Date 2020/1/23 10:47
 * @Message
 */

public class JwtToken implements AuthenticationToken {
    private static final long serialVersionUID = 4433221178798772743L;
    private String token;


    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {

        return this.token;
    }

    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }
}
