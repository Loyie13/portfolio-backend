package com.Portfolio.service;

import com.Portfolio.dto.SkillRequest;
import com.Portfolio.dto.SkillResponse;
import com.Portfolio.model.Skill;
import com.Portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillResponse createSkill(SkillRequest request) {
        if (skillRepository.existsByName(request.getName())) {
            throw new RuntimeException("Skill '" + request.getName() + "' already exists");
        }

        Skill skill = new Skill();
        mapRequestToSkill(request, skill);

        Skill savedSkill = skillRepository.save(skill);
        return mapSkillToResponse(savedSkill);
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAllByOrderByCategoryAscDisplayOrderAsc()
                .stream()
                .map(this::mapSkillToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> getSkillsByCategory(String category) {
        return skillRepository.findByCategoryOrderByDisplayOrderAsc(category)
                .stream()
                .map(this::mapSkillToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> getFeaturedSkills() {
        return skillRepository.findByFeaturedTrue()
                .stream()
                .map(this::mapSkillToResponse)
                .collect(Collectors.toList());
    }

    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));

        mapRequestToSkill(request, skill);
        Skill updatedSkill = skillRepository.save(skill);
        return mapSkillToResponse(updatedSkill);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    private void mapRequestToSkill(SkillRequest request, Skill skill) {
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setProficiency(request.getProficiency());
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setIconUrl(request.getIconUrl());
        skill.setFeatured(request.getFeatured() != null ? request.getFeatured() : false);
    }

    private SkillResponse mapSkillToResponse(Skill skill) {
        SkillResponse response = new SkillResponse();
        response.setId(skill.getId());
        response.setName(skill.getName());
        response.setCategory(skill.getCategory());
        response.setProficiency(skill.getProficiency());
        response.setDisplayOrder(skill.getDisplayOrder());
        response.setIconUrl(skill.getIconUrl());
        response.setFeatured(skill.getFeatured());
        return response;
    }
}