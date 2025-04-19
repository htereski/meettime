package com.meettime.hubspot.integrations.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "hubspot")
@Data
public class HubspotProperties {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Value("${SCOPE}")
    private String scopes;

    @Value("${REDIRECT_URI}")
    private String redirectUri;

    @Value("${AUTH_URL}")
    private String authUrl;

    @Value("${TOKEN_URL}")
    private String tokenUrl;
}
