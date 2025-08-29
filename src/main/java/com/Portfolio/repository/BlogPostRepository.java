package com.Portfolio.repository;

import com.Portfolio.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findByPublishedTrueOrderByPublishedAtDesc();

    List<BlogPost> findByFeaturedTrueAndPublishedTrueOrderByPublishedAtDesc();

    List<BlogPost> findByCategoryAndPublishedTrueOrderByPublishedAtDesc(String category);

    Optional<BlogPost> findBySlugAndPublishedTrue(String slug);

    List<BlogPost> findByTagsContainingAndPublishedTrueOrderByPublishedAtDesc(String tag);

    List<BlogPost> findByPublished(Boolean published);

    boolean existsBySlug(String slug);

    boolean existsByTitle(String title);

    @Query("SELECT DISTINCT bp.category FROM BlogPost bp WHERE bp.published = true")
    List<String> findAllDistinctCategories();

    @Query("SELECT DISTINCT tag FROM BlogPost bp JOIN bp.tags tag WHERE bp.published = true")
    List<String> findAllDistinctTags();
}