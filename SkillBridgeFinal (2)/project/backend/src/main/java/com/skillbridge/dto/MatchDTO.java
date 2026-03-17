package com.skillbridge.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MatchDTO {
    private UUID userId;
    private String userName;
    private String userLocation;
    private BigDecimal userReputation;
    private UUID skillTheyOffer;
    private UUID skillTheyNeed;
}
