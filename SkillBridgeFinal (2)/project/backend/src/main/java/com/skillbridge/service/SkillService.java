package com.skillbridge.service;

import com.skillbridge.model.Skill;
import com.skillbridge.model.UserSkill;
import com.skillbridge.repository.SkillRepository;
import com.skillbridge.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    public Skill createSkill(String skillName, String category) {
        if (skillRepository.existsBySkillName(skillName)) {
            return skillRepository.findBySkillName(skillName).get();
        }

        Skill skill = new Skill();
        skill.setSkillName(skillName);
        skill.setCategory(category);
        return skillRepository.save(skill);
    }

    public Skill getSkillById(UUID id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public Skill getSkillByName(String skillName) {
        return skillRepository.findBySkillName(skillName)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public UserSkill addUserSkill(UUID userId, UUID skillId, String skillType, String proficiencyLevel, String description) {
        UserSkill existing = userSkillRepository.findByUserIdAndSkillIdAndSkillType(userId, skillId, skillType);
        if (existing != null) {
            throw new RuntimeException("User skill already exists");
        }

        UserSkill userSkill = new UserSkill();
        userSkill.setUserId(userId);
        userSkill.setSkillId(skillId);
        userSkill.setSkillType(skillType);
        userSkill.setProficiencyLevel(proficiencyLevel);
        userSkill.setDescription(description);
        return userSkillRepository.save(userSkill);
    }

    public List<UserSkill> getUserSkills(UUID userId) {
        return userSkillRepository.findByUserId(userId);
    }

    public List<UserSkill> getUserSkillsByType(UUID userId, String skillType) {
        return userSkillRepository.findByUserIdAndSkillType(userId, skillType);
    }

    @Transactional
    public void removeUserSkill(UUID userId, UUID skillId, String skillType) {
        userSkillRepository.deleteByUserIdAndSkillIdAndSkillType(userId, skillId, skillType);
    }
}
