package com.bilgeadam.controller;


import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailSenderService;

    @GetMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String email){

        return ResponseEntity.ok(mailSenderService.sendMail(email));
    }

}
