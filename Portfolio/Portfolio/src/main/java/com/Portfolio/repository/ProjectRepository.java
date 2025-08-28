package com.Portfolio.repository;

import com.Portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByPublishedTrue();

    List<Project> findByFeaturedTrueAndPublishedTrue();

    List<Project> findByPublished(Boolean published);

    Optional<Project> findByIdAndPublishedTrue(Long id);

    @Query("SELECT p FROM Project p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) AND p.published = true")
    List<Project> searchPublishedProjects(String query);

    boolean existsByTitle(String title);
}