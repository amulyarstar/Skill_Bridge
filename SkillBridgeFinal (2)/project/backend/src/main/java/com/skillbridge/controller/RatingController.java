package com.skillbridge.controller;

import com.skillbridge.model.Rating;
import com.skillbridge.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<?> createRating(@RequestBody Map<String, String> request) {
        try {
            Rating rating = ratingService.createRating(
                    UUID.fromString(request.get("exchangeId")),
                    UUID.fromString(request.get("raterId")),
                    UUID.fromString(request.get("ratedId")),
                    Integer.parseInt(request.get("rating")),
                    request.get("review")
            );
            return ResponseEntity.ok(rating);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getUserRatings(@PathVariable UUID userId) {
        List<Rating> ratings = ratingService.getUserRatings(userId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/exchange/{exchangeId}")
    public ResponseEntity<List<Rating>> getExchangeRatings(@PathVariable UUID exchangeId) {
        List<Rating> ratings = ratingService.getExchangeRatings(exchangeId);
        return ResponseEntity.ok(ratings);
    }
}
