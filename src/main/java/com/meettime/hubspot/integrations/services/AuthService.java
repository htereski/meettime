package com.meettime.hubspot.integrations.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import com.meettime.hubspot.integrations.configs.HubspotProperties;
import com.meettime.hubspot.integrations.responses.TokenResponse;

@Service
public class AuthService {

    @Autowired
    private HubspotProperties properties;

    private RestTemplate restTemplate = new RestTemplate();

    public RedirectView buildAuthorizationUrl() {
        String url = UriComponentsBuilder
                .newInstance()
                .uri(URI.create(properties.getAuthUrl()))
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("scope", properties.getScopes())
                .queryParam("response_type", "code")
                .build()
                .toUriString();

        return new RedirectView(url);
    }

    public ResponseEntity<String> authorizeWithCode(String code) {
        TokenResponse token = this.requestAccessToken(code);
        String msg = "Autorizado com sucesso! Access token: ".concat(token.getAccessToken());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    private TokenResponse requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", properties.getClientId());
        form.add("client_secret", properties.getClientSecret());
        form.add("redirect_uri", properties.getRedirectUri());
        form.add("code", code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        ResponseEntity<TokenResponse> response = this.restTemplate.postForEntity(
                properties.getTokenUrl(),
                entity,
                TokenResponse.class);
        return response.getBody();
    }
}
