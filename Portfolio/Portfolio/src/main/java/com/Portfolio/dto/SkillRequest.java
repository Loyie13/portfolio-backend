package com.Portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SkillRequest {

    @NotBlank(message = "Skill name is required")
    @Size(max = 50, message = "Skill name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    private Integer proficiency; // 1-100 (optional)
    private Integer displayOrder; // For sorting
    private String iconUrl;      // URL to icon
    private Boolean featured;    // Is this a featured skill?
}