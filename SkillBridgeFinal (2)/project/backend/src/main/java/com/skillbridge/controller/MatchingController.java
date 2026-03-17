package com.skillbridge.controller;

import com.skillbridge.dto.MatchDTO;
import com.skillbridge.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "*")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<MatchDTO>> findMatches(@PathVariable UUID userId) {
        List<MatchDTO> matches = matchingService.findMatches(userId);
        return ResponseEntity.ok(matches);
    }
}
