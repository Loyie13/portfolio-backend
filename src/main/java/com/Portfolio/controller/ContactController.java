package com.Portfolio.controller;

import com.Portfolio.model.ContactMessage;
import com.Portfolio.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*", allowCredentials = "false")// Your React app URL
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@Valid @RequestBody ContactMessage contactMessage, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            ContactMessage savedMessage = contactService.saveMessage(contactMessage);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Message sent successfully!");
            response.put("id", savedMessage.getId().toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error sending message: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}