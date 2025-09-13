package com.Portfolio.controller;

import com.Portfolio.dto.BlogPostRequest;
import com.Portfolio.dto.BlogPostResponse;
import com.Portfolio.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public ResponseEntity<List<BlogPostResponse>> getAllPublishedBlogPosts() {
        return ResponseEntity.ok(blogPostService.getAllPublishedBlogPosts());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<BlogPostResponse>> getFeaturedBlogPosts() {
        return ResponseEntity.ok(blogPostService.getFeaturedBlogPosts());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<BlogPostResponse> getBlogPostBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(blogPostService.getBlogPostBySlug(slug));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogPostResponse>> getBlogPostsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(blogPostService.getBlogPostsByCategory(category));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<BlogPostResponse>> getBlogPostsByTag(@PathVariable String tag) {
        return ResponseEntity.ok(blogPostService.getBlogPostsByTag(tag));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(blogPostService.getAllCategories());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        return ResponseEntity.ok(blogPostService.getAllTags());
    }

    @PostMapping
    public ResponseEntity<BlogPostResponse> createBlogPost(@Valid @RequestBody BlogPostRequest request) {
        return ResponseEntity.ok(blogPostService.createBlogPost(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponse> updateBlogPost(
            @PathVariable Long id,
            @Valid @RequestBody BlogPostRequest request) {
        return ResponseEntity.ok(blogPostService.updateBlogPost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<BlogPostResponse> togglePublishStatus(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.togglePublishStatus(id));
    }
}