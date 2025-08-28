package com.Portfolio.service;

import com.Portfolio.dto.ProjectRequest;
import com.Portfolio.dto.ProjectResponse;
import com.Portfolio.model.Project;
import com.Portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;


    public ProjectResponse createProject(ProjectRequest request) {
        // Check if project with same title already exists
        if (projectRepository.existsByTitle(request.getTitle())) {
            throw new RuntimeException("Project with title '" + request.getTitle() + "' already exists");
        }

        Project project = new Project();
        mapRequestToProject(request, project);

        Project savedProject = projectRepository.save(project);
        return mapProjectToResponse(savedProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllPublishedProjects() {
        return projectRepository.findByPublishedTrue()
                .stream()
                .map(this::mapProjectToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getFeaturedProjects() {
        return projectRepository.findByFeaturedTrueAndPublishedTrue()
                .stream()
                .map(this::mapProjectToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findByIdAndPublishedTrue(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return mapProjectToResponse(project);
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        mapRequestToProject(request, project);
        Project updatedProject = projectRepository.save(project);
        return mapProjectToResponse(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    public ProjectResponse togglePublishStatus(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        project.setPublished(!project.getPublished());
        Project updatedProject = projectRepository.save(project);
        return mapProjectToResponse(updatedProject);
    }

    private void mapRequestToProject(ProjectRequest request, Project project) {
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setDetailedDescription(request.getDetailedDescription());
        project.setTechnologies(request.getTechnologies());
        project.setImageUrl(request.getImageUrl());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setFeatured(request.getFeatured() != null ? request.getFeatured() : false);
        project.setPublished(request.getPublished() != null ? request.getPublished() : false);
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
    }

    private ProjectResponse mapProjectToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setDetailedDescription(project.getDetailedDescription());
        response.setTechnologies(project.getTechnologies());
        response.setImageUrl(project.getImageUrl());
        response.setProjectUrl(project.getProjectUrl());
        response.setGithubUrl(project.getGithubUrl());
        response.setFeatured(project.getFeatured());
        response.setPublished(project.getPublished());
        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        return response;
    }
}