package com.example.demo.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;


public class CustomUsernamePasswordToken  extends UsernamePasswordToken {
    private String jwtToken;

    public CustomUsernamePasswordToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
