package com.Portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 1000, message = "Detailed description must be less than 1000 characters")
    private String detailedDescription;

    private List<String> technologies;
    private String imageUrl;
    private String projectUrl;
    private String githubUrl;
    private Boolean featured;
    private Boolean published;
    private LocalDate startDate;
    private LocalDate endDate;
}