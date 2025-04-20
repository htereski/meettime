package com.meettime.hubspot.integrations.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "hubspot")
@Data
public class HubspotProperties {

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${scope}")
    private String scopes;

    @Value("${redirect_uri}")
    private String redirectUri;

    @Value("${auth_url}")
    private String authUrl;

    @Value("${token_url}")
    private String tokenUrl;
}
