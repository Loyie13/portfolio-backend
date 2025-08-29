package com.Portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_posts")
@Data
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(max = 220, message = "Slug must be less than 220 characters")
    @Column(nullable = false, unique = true, length = 220)
    private String slug; // SEO-friendly URL: "my-awesome-post"

    @NotBlank(message = "Content is required")
    @Column(columnDefinition = "TEXT") // Allows long text content
    private String content;

    @Size(max = 500, message = "Excerpt must be less than 500 characters")
    private String excerpt; // Short summary for preview

    @ElementCollection
    @CollectionTable(name = "blog_post_tags", joinColumns = @JoinColumn(name = "blog_post_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    private String category; // e.g., "Tutorial", "News", "Personal"

    @Column(name = "featured_image_url")
    private String featuredImageUrl;

    @Column(name = "published")
    private Boolean published = false;

    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (published) {
            publishedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (published && publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }
}