package com.meettime.hubspot.integrations.configs;

import org.springframework.stereotype.Component;

@Component
public class TokenStore {
    private String accessToken;

    public void saveToken(String token) {
        this.accessToken = token;
    }

    public String getToken() {
        return this.accessToken;
    }
}
