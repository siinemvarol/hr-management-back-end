package com.bilgeadam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender mailSender;
    public MailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }
    public String sendMail(String mail){
        System.out.println(mail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("keremturak@gmail.com");
        message.setTo(mail);
        message.setText("Doğrulama Kodunuz :");
        mailSender.send(message);
        return "Mail Gönderildi";
    }
}
