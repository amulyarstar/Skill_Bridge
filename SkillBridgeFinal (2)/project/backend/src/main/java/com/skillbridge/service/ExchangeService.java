package com.skillbridge.service;

import com.skillbridge.model.Exchange;
import com.skillbridge.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    public Exchange createExchange(UUID requesterId, UUID partnerId, UUID skillGivenId, UUID skillReceivedId, String message) {
        Exchange exchange = new Exchange();
        exchange.setRequesterId(requesterId);
        exchange.setPartnerId(partnerId);
        exchange.setSkillGivenId(skillGivenId);
        exchange.setSkillReceivedId(skillReceivedId);
        exchange.setMessage(message);
        exchange.setStatus("PENDING");
        return exchangeRepository.save(exchange);
    }

    public Exchange getExchangeById(UUID id) {
        return exchangeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange not found"));
    }

    public List<Exchange> getUserExchanges(UUID userId) {
        return exchangeRepository.findByUserId(userId);
    }

    public Exchange updateExchangeStatus(UUID exchangeId, String status) {
        Exchange exchange = getExchangeById(exchangeId);
        exchange.setStatus(status);

        if (status.equals("COMPLETED")) {
            exchange.setCompletedAt(LocalDateTime.now());
        }

        return exchangeRepository.save(exchange);
    }

    public List<Exchange> getExchangesByStatus(String status) {
        return exchangeRepository.findByStatus(status);
    }
}
