package com.bilgeadam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/auth")
    public ResponseEntity<String> authServiceFallback(){
        return ResponseEntity.ok("Auth service is currently unavailable.");
    }
    @GetMapping("/user")
    public ResponseEntity<String> userServiceFallback(){
            return ResponseEntity.ok("User service is currently unavailable.");
    }
    @GetMapping("/mail")
    public ResponseEntity<String> mailServiceFallback(){
        return ResponseEntity.ok("Mail service is currently unavailable.");
    }
    @GetMapping("/company")
    public ResponseEntity<String> companyServiceFallback(){
        return ResponseEntity.ok("Company service is currently unavailable.");
    }
    @GetMapping("/comment")
    public ResponseEntity<String> commentServiceFallback(){
        return ResponseEntity.ok("Comment service is currently unavailable.");
    }
}
