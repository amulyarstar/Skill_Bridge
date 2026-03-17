package com.skillbridge.repository;

import com.skillbridge.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    List<Rating> findByRatedId(UUID ratedId);
    List<Rating> findByRaterId(UUID raterId);
    List<Rating> findByExchangeId(UUID exchangeId);
    boolean existsByExchangeIdAndRaterId(UUID exchangeId, UUID raterId);
}
