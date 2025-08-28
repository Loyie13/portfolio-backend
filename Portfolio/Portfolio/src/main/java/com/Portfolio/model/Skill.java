package com.Portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "skills")
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Size(max = 50, message = "Skill name must be less than 50 characters")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be less than 50 characters")
    @Column(nullable = false, length = 50)
    private String category; // e.g., "Frontend", "Backend", "Tools"

    @Column(name = "proficiency")
    private Integer proficiency; // 1-100 (optional)

    @Column(name = "display_order")
    private Integer displayOrder; // For sorting skills

    @Column(name = "icon_url")
    private String iconUrl; // URL to skill icon/image

    @Column(name = "featured")
    private Boolean featured = false;
}