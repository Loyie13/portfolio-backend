package com.Portfolio.dto;

import lombok.Data;

@Data
public class SkillResponse {
    private Long id;
    private String name;
    private String category;
    private Integer proficiency;
    private Integer displayOrder;
    private String iconUrl;
    private Boolean featured;
}