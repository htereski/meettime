package com.meettime.hubspot.integrations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException ex) {
        String msg = ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
    }
}
