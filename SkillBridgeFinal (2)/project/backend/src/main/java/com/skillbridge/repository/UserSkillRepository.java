package com.skillbridge.repository;

import com.skillbridge.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, UUID> {
    List<UserSkill> findByUserId(UUID userId);
    List<UserSkill> findByUserIdAndSkillType(UUID userId, String skillType);

    @Query("SELECT us FROM UserSkill us WHERE us.userId = :userId AND us.skillId = :skillId AND us.skillType = :skillType")
    UserSkill findByUserIdAndSkillIdAndSkillType(
        @Param("userId") UUID userId,
        @Param("skillId") UUID skillId,
        @Param("skillType") String skillType
    );

    void deleteByUserIdAndSkillIdAndSkillType(UUID userId, UUID skillId, String skillType);
}
