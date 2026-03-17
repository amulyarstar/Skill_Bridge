package com.skillbridge.controller;

import com.skillbridge.model.User;
import com.skillbridge.model.Exchange;
import com.skillbridge.service.UserService;
import com.skillbridge.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exchanges")
    public ResponseEntity<List<Exchange>> getAllExchanges(@RequestParam(required = false) String status) {
        if (status != null) {
            List<Exchange> exchanges = exchangeService.getExchangesByStatus(status);
            return ResponseEntity.ok(exchanges);
        }
        return ResponseEntity.ok(List.of());
    }
}
