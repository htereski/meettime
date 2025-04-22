package com.meettime.hubspot.integrations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.meettime.hubspot.integrations.configs.RateLimited;
import com.meettime.hubspot.integrations.requests.ContactRequest;
import com.meettime.hubspot.integrations.services.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/contacts")
@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RateLimited
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ContactRequest contact) {
        return this.contactService.create(contact);
    }

}
