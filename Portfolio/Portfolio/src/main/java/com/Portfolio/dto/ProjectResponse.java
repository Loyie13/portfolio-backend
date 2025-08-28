package com.Portfolio.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String detailedDescription;
    private List<String> technologies;
    private String imageUrl;
    private String projectUrl;
    private String githubUrl;
    private Boolean featured;
    private Boolean published;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}