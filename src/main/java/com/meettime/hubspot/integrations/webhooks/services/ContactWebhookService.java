package com.meettime.hubspot.integrations.webhooks.services;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.meettime.hubspot.integrations.webhooks.responses.ContactWebhookResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ContactWebhookService {

    public ResponseEntity<Void> process(List<ContactWebhookResponse> webhooks) {
        log.info("Recebendo webhooks de contatos: "
                .concat(String.valueOf(webhooks.size()))
                .concat(" webhooks recebidos."));

        webhooks.stream().forEach(webhook -> {
            log.info("Webhook recebido: ".concat(webhook.toString()));
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
