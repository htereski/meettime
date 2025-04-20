package com.meettime.hubspot.integrations.webhooks.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.meettime.hubspot.integrations.webhooks.responses.ContactWebhookResponse;
import com.meettime.hubspot.integrations.webhooks.services.ContactWebhookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/hubspot/contact")
@Controller
public class ContactWebhook {

    @Autowired
    private ContactWebhookService contactWebhookService;

    @PostMapping("/created")
    public ResponseEntity<Void> handleCreated(@RequestBody List<ContactWebhookResponse> webhookList) {
        return this.contactWebhookService.process(webhookList);
    }

}
