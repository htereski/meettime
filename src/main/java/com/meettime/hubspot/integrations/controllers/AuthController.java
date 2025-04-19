package com.meettime.hubspot.integrations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.meettime.hubspot.integrations.services.AuthService;

@RequestMapping("/oauth")
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hubspot/redirect")
    public RedirectView redirectToHubspot() {
        return this.authService.buildAuthorizationUrl();
    }

    @GetMapping("/hubspot/callback")
    public ResponseEntity<String> authorizeWithCode(@RequestParam String code) {
        return this.authService.authorizeWithCode(code);
    }

}
