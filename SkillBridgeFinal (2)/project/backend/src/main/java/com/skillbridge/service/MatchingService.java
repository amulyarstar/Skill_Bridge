package com.skillbridge.service;

import com.skillbridge.dto.MatchDTO;
import com.skillbridge.model.User;
import com.skillbridge.model.UserSkill;
import com.skillbridge.repository.UserSkillRepository;
import com.skillbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MatchDTO> findMatches(UUID userId) {
        List<UserSkill> userOffers = userSkillRepository.findByUserIdAndSkillType(userId, "OFFER");
        List<UserSkill> userRequests = userSkillRepository.findByUserIdAndSkillType(userId, "REQUEST");

        if (userOffers.isEmpty() || userRequests.isEmpty()) {
            return new ArrayList<>();
        }

        Set<UUID> offeredSkillIds = userOffers.stream()
                .map(UserSkill::getSkillId)
                .collect(Collectors.toSet());

        Set<UUID> requestedSkillIds = userRequests.stream()
                .map(UserSkill::getSkillId)
                .collect(Collectors.toSet());

        List<UserSkill> allUserSkills = userSkillRepository.findAll();

        Map<UUID, List<UserSkill>> userSkillMap = allUserSkills.stream()
                .filter(us -> !us.getUserId().equals(userId))
                .collect(Collectors.groupingBy(UserSkill::getUserId));

        List<MatchDTO> matches = new ArrayList<>();

        for (Map.Entry<UUID, List<UserSkill>> entry : userSkillMap.entrySet()) {
            UUID otherUserId = entry.getKey();
            List<UserSkill> otherUserSkills = entry.getValue();

            List<UserSkill> otherOffers = otherUserSkills.stream()
                    .filter(us -> us.getSkillType().equals("OFFER"))
                    .collect(Collectors.toList());

            List<UserSkill> otherRequests = otherUserSkills.stream()
                    .filter(us -> us.getSkillType().equals("REQUEST"))
                    .collect(Collectors.toList());

            for (UserSkill myOffer : userOffers) {
                for (UserSkill myRequest : userRequests) {
                    boolean otherOffersWhatINeed = otherOffers.stream()
                            .anyMatch(offer -> offer.getSkillId().equals(myRequest.getSkillId()));

                    boolean otherNeedsWhatIOffer = otherRequests.stream()
                            .anyMatch(request -> request.getSkillId().equals(myOffer.getSkillId()));

                    if (otherOffersWhatINeed && otherNeedsWhatIOffer) {
                        User otherUser = userRepository.findById(otherUserId).orElse(null);
                        if (otherUser != null) {
                            MatchDTO match = new MatchDTO();
                            match.setUserId(otherUserId);
                            match.setUserName(otherUser.getName());
                            match.setUserLocation(otherUser.getLocation());
                            match.setUserReputation(otherUser.getReputationScore());
                            match.setSkillTheyOffer(myRequest.getSkillId());
                            match.setSkillTheyNeed(myOffer.getSkillId());
                            matches.add(match);
                            break;
                        }
                    }
                }
            }
        }

        return matches.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
