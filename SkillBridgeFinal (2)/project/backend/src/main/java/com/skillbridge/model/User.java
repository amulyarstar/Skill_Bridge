package com.skillbridge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String location;

    @Column(name = "reputation_score", precision = 3, scale = 2)
    private BigDecimal reputationScore;

    @Column(name = "total_ratings")
    private Integer totalRatings;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (reputationScore == null) {
            reputationScore = BigDecimal.ZERO;
        }
        if (totalRatings == null) {
            totalRatings = 0;
        }
        if (isAdmin == null) {
            isAdmin = false;
        }
        if (bio == null) {
            bio = "";
        }
        if (location == null) {
            location = "";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
