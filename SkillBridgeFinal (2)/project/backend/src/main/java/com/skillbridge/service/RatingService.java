package com.skillbridge.service;

import com.skillbridge.model.Rating;
import com.skillbridge.model.Exchange;
import com.skillbridge.repository.RatingRepository;
import com.skillbridge.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    public Rating createRating(UUID exchangeId, UUID raterId, UUID ratedId, Integer rating, String review) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new RuntimeException("Exchange not found"));

        if (!exchange.getStatus().equals("COMPLETED")) {
            throw new RuntimeException("Can only rate completed exchanges");
        }

        if (!exchange.getRequesterId().equals(raterId) && !exchange.getPartnerId().equals(raterId)) {
            throw new RuntimeException("Only exchange participants can rate");
        }

        if (ratingRepository.existsByExchangeIdAndRaterId(exchangeId, raterId)) {
            throw new RuntimeException("You have already rated this exchange");
        }

        Rating ratingEntity = new Rating();
        ratingEntity.setExchangeId(exchangeId);
        ratingEntity.setRaterId(raterId);
        ratingEntity.setRatedId(ratedId);
        ratingEntity.setRating(rating);
        ratingEntity.setReview(review);

        return ratingRepository.save(ratingEntity);
    }

    public List<Rating> getUserRatings(UUID userId) {
        return ratingRepository.findByRatedId(userId);
    }

    public List<Rating> getExchangeRatings(UUID exchangeId) {
        return ratingRepository.findByExchangeId(exchangeId);
    }
}
