package com.Portfolio.service;

import com.Portfolio.dto.BlogPostRequest;
import com.Portfolio.dto.BlogPostResponse;
import com.Portfolio.model.BlogPost;
import com.Portfolio.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostResponse createBlogPost(BlogPostRequest request) {
        if (blogPostRepository.existsByTitle(request.getTitle())) {
            throw new RuntimeException("Blog post with title '" + request.getTitle() + "' already exists");
        }

        BlogPost blogPost = new BlogPost();
        mapRequestToBlogPost(request, blogPost);

        // Generate slug from title
        String slug = generateSlug(request.getTitle());
        blogPost.setSlug(slug);

        BlogPost savedBlogPost = blogPostRepository.save(blogPost);
        return mapBlogPostToResponse(savedBlogPost);
    }

    @Transactional(readOnly = true)
    public List<BlogPostResponse> getAllPublishedBlogPosts() {
        return blogPostRepository.findByPublishedTrueOrderByPublishedAtDesc()
                .stream()
                .map(this::mapBlogPostToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BlogPostResponse> getFeaturedBlogPosts() {
        return blogPostRepository.findByFeaturedTrueAndPublishedTrueOrderByPublishedAtDesc()
                .stream()
                .map(this::mapBlogPostToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BlogPostResponse getBlogPostBySlug(String slug) {
        BlogPost blogPost = blogPostRepository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new RuntimeException("Blog post not found with slug: " + slug));

        // Increment view count
        blogPost.setViewCount(blogPost.getViewCount() + 1);
        blogPostRepository.save(blogPost);

        return mapBlogPostToResponse(blogPost);
    }

    @Transactional(readOnly = true)
    public List<BlogPostResponse> getBlogPostsByCategory(String category) {
        return blogPostRepository.findByCategoryAndPublishedTrueOrderByPublishedAtDesc(category)
                .stream()
                .map(this::mapBlogPostToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BlogPostResponse> getBlogPostsByTag(String tag) {
        return blogPostRepository.findByTagsContainingAndPublishedTrueOrderByPublishedAtDesc(tag)
                .stream()
                .map(this::mapBlogPostToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return blogPostRepository.findAllDistinctCategories();
    }

    @Transactional(readOnly = true)
    public List<String> getAllTags() {
        return blogPostRepository.findAllDistinctTags();
    }

    public BlogPostResponse updateBlogPost(Long id, BlogPostRequest request) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        mapRequestToBlogPost(request, blogPost);
        BlogPost updatedBlogPost = blogPostRepository.save(blogPost);
        return mapBlogPostToResponse(updatedBlogPost);
    }

    public void deleteBlogPost(Long id) {
        if (!blogPostRepository.existsById(id)) {
            throw new RuntimeException("Blog post not found with id: " + id);
        }
        blogPostRepository.deleteById(id);
    }

    public BlogPostResponse togglePublishStatus(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found with id: " + id));

        blogPost.setPublished(!blogPost.getPublished());
        if (blogPost.getPublished()) {
            blogPost.setPublishedAt(LocalDateTime.now());
        }

        BlogPost updatedBlogPost = blogPostRepository.save(blogPost);
        return mapBlogPostToResponse(updatedBlogPost);
    }

    private void mapRequestToBlogPost(BlogPostRequest request, BlogPost blogPost) {
        blogPost.setTitle(request.getTitle());
        blogPost.setContent(request.getContent());
        blogPost.setExcerpt(request.getExcerpt());
        blogPost.setTags(request.getTags());
        blogPost.setCategory(request.getCategory());
        blogPost.setFeaturedImageUrl(request.getFeaturedImageUrl());
        blogPost.setPublished(request.getPublished() != null ? request.getPublished() : false);
        blogPost.setFeatured(request.getFeatured() != null ? request.getFeatured() : false);
    }

    private BlogPostResponse mapBlogPostToResponse(BlogPost blogPost) {
        BlogPostResponse response = new BlogPostResponse();
        response.setId(blogPost.getId());
        response.setTitle(blogPost.getTitle());
        response.setSlug(blogPost.getSlug());
        response.setContent(blogPost.getContent());
        response.setExcerpt(blogPost.getExcerpt());
        response.setTags(blogPost.getTags());
        response.setCategory(blogPost.getCategory());
        response.setFeaturedImageUrl(blogPost.getFeaturedImageUrl());
        response.setPublished(blogPost.getPublished());
        response.setFeatured(blogPost.getFeatured());
        response.setViewCount(blogPost.getViewCount());
        response.setCreatedAt(blogPost.getCreatedAt());
        response.setUpdatedAt(blogPost.getUpdatedAt());
        response.setPublishedAt(blogPost.getPublishedAt());
        return response;
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special characters
                .replaceAll("\\s+", "-")         // Replace spaces with hyphens
                .replaceAll("-+", "-")           // Replace multiple hyphens with single
                .trim();
    }
}