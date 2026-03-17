package com.skillbridge.controller;

import com.skillbridge.model.Skill;
import com.skillbridge.model.UserSkill;
import com.skillbridge.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    @PostMapping
    public ResponseEntity<?> createSkill(@RequestBody Map<String, String> request) {
        try {
            Skill skill = skillService.createSkill(
                    request.get("skillName"),
                    request.get("category")
            );
            return ResponseEntity.ok(skill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkill>> getUserSkills(@PathVariable UUID userId) {
        List<UserSkill> userSkills = skillService.getUserSkills(userId);
        return ResponseEntity.ok(userSkills);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<UserSkill>> getUserSkillsByType(
            @PathVariable UUID userId,
            @PathVariable String type) {
        List<UserSkill> userSkills = skillService.getUserSkillsByType(userId, type);
        return ResponseEntity.ok(userSkills);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUserSkill(@RequestBody Map<String, String> request) {
        try {
            UserSkill userSkill = skillService.addUserSkill(
                    UUID.fromString(request.get("userId")),
                    UUID.fromString(request.get("skillId")),
                    request.get("skillType"),
                    request.get("proficiencyLevel"),
                    request.get("description")
            );
            return ResponseEntity.ok(userSkill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}/skill/{skillId}/type/{type}")
    public ResponseEntity<?> removeUserSkill(
            @PathVariable UUID userId,
            @PathVariable UUID skillId,
            @PathVariable String type) {
        try {
            skillService.removeUserSkill(userId, skillId, type);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
