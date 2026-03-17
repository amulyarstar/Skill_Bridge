package com.skillbridge.controller;

import com.skillbridge.model.Exchange;
import com.skillbridge.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/exchanges")
@CrossOrigin(origins = "*")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<?> createExchange(@RequestBody Map<String, String> request) {
        try {
            Exchange exchange = exchangeService.createExchange(
                    UUID.fromString(request.get("requesterId")),
                    UUID.fromString(request.get("partnerId")),
                    UUID.fromString(request.get("skillGivenId")),
                    UUID.fromString(request.get("skillReceivedId")),
                    request.get("message")
            );
            return ResponseEntity.ok(exchange);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exchange> getExchangeById(@PathVariable UUID id) {
        try {
            Exchange exchange = exchangeService.getExchangeById(id);
            return ResponseEntity.ok(exchange);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Exchange>> getUserExchanges(@PathVariable UUID userId) {
        List<Exchange> exchanges = exchangeService.getUserExchanges(userId);
        return ResponseEntity.ok(exchanges);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateExchangeStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request) {
        try {
            Exchange exchange = exchangeService.updateExchangeStatus(id, request.get("status"));
            return ResponseEntity.ok(exchange);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
