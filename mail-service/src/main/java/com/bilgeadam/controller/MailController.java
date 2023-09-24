package com.bilgeadam.controller;



import com.bilgeadam.entity.Mail;
import com.bilgeadam.rabbitmq.consumer.MailRegisterConsumer;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static com.bilgeadam.constant.RestApiList.MAIL;
import static com.bilgeadam.constant.RestApiList.MAILSEND;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIL)
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
public class MailController {
    private final MailService mailSenderService;

    @PostMapping(MAILSEND)
    public ResponseEntity<?> sendMessage(@RequestBody MailRegisterModel mail) throws MessagingException {

        return ResponseEntity.ok(mailSenderService.sendMail(mail));

    }

}
