package com.bilgeadam.controller;



import com.bilgeadam.entity.Mail;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.RestApiList.MAIL;
import static com.bilgeadam.constant.RestApiList.MAILSEND;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIL)
public class MailController {
    private final MailService mailSenderService;

    @PostMapping(MAILSEND)
    public ResponseEntity<?> sendMessage(@RequestBody Mail mail){

        return ResponseEntity.ok(mailSenderService.sendMail(mail));

    }

}
