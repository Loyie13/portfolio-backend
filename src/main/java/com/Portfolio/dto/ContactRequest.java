package com.Portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// A "record" is a modern, concise way to create an immutable data carrier class in Java.
// It automatically generates the constructor, getters, equals(), hashCode(), and toString().
public record ContactRequest(
        @NotBlank(message = "Name is required.") // Ensures the name is not null or just whitespace
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
        String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Please enter a valid email address.") // Validates email format
        String email,

        @NotBlank(message = "Message is required.")
        @Size(min = 10, max = 5000, message = "Message must be between 10 and 5000 characters.")
        String message
) {}