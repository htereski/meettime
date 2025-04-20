package com.meettime.hubspot.integrations.services;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.meettime.hubspot.integrations.configs.TokenStore;
import com.meettime.hubspot.integrations.requests.ContactRequest;
import com.meettime.hubspot.integrations.responses.ContactResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ContactService {

    @Value("${hubspot.endpoint.create.contact}")
    private String urlCreateContact;

    @Autowired
    private TokenStore tokenStore;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> create(ContactRequest contact) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.tokenStore.getToken());

        Map<String, Object> properties = new HashMap<>();
        properties.put("email", contact.getEmail());
        properties.put("firstname", contact.getFirstname());
        properties.put("lastname", contact.getLastname());
        properties.put("phone", contact.getPhone());
        properties.put("company", contact.getCompany());
        properties.put("website", contact.getWebsite());
        properties.put("lifecyclestage", contact.getLifecyclestage());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            this.restTemplate.postForEntity(
                    this.urlCreateContact,
                    entity,
                    ContactResponse.class);

            log.info("Contact created successfully: ".concat(contact.toString()));
            return ResponseEntity.status(HttpStatus.OK).body("Contact created successfully");
        } catch (HttpClientErrorException e) {
            log.error("Error creating contact: ".concat(e.getMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Contact already exists");
        }
    }

}
