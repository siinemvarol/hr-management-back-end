package com.bilgeadam.controller;



import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static com.bilgeadam.constant.RestApiList.MAIL;
import static com.bilgeadam.constant.RestApiList.MAIL_SEND;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIL)
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
public class MailController {
    private final MailService mailSenderService;

    @PostMapping(MAIL_SEND)
    public ResponseEntity<?> sendMessage(@RequestBody MailRegisterModel mail) throws MessagingException {

        return ResponseEntity.ok(mailSenderService.sendMail(mail));

    }

}
