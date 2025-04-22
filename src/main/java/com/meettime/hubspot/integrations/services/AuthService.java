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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import com.meettime.hubspot.integrations.configs.HubspotProperties;
import com.meettime.hubspot.integrations.configs.TokenStore;
import com.meettime.hubspot.integrations.responses.TokenResponse;
import lombok.extern.log4j.Log4j2;
import static java.util.Objects.isNull;

@Log4j2
@Service
public class AuthService {

    @Autowired
    private HubspotProperties properties;

    @Autowired
    private TokenStore tokenStore;

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
        String msg = "Authorized successfully! Access token: ".concat(token.getAccessToken());
        log.info(msg);
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

        try {
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
            ResponseEntity<TokenResponse> response = this.restTemplate.postForEntity(
                    this.properties.getTokenUrl(),
                    entity,
                    TokenResponse.class);

            if (isNull(response.getBody())) {
                throw new RestClientException("Failed to get access token from HubSpot");
            }

            this.tokenStore.saveToken(response.getBody().getAccessToken());

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("HTTP error when requesting token: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString(),
                    ex);
            throw new RestClientException("HTTP error when requesting token", ex);
        } catch (ResourceAccessException ex) {
            log.error("Resource access error (timeout, connection): {}", ex.getMessage(), ex);
            throw new RestClientException("Connection error when requesting token", ex);
        } catch (RestClientException ex) {
            log.error("Error requesting token: {}", ex.getMessage(), ex);
            throw new RestClientException("Error requesting token", ex);
        }
    }
}
