package com.Portfolio.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogPostResponse {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String excerpt;
    private List<String> tags;
    private String category;
    private String featuredImageUrl;
    private Boolean published;
    private Boolean featured;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
}