package com.bilgeadam.service;


import com.bilgeadam.entity.Mail;

import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.MailException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {


    private final JavaMailSender mailSender;


    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendMail(Mail mail) {
    try {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("your_email@example.com"); // Şirketinizi temsil eden e-posta adresi
        mailMessage.setTo(mail.getEmail());
        mailMessage.setSubject("Şifreniz: ");
        mailMessage.setText("Merhaba, Neredeyse işleminiz tamamlandı.  \n\nLütfen Aktivasyon kodunu giriniz...\n\nAktivasyon Kodunuz:   " + mail.getActivationCode());

        mailSender.send(mailMessage);
        System.out.println("Mail Gönderildi");
        return "Başarılı";
    }catch (MailException e) {
        throw new MailException(ErrorType.MAIL_SEND_ERROR);
    }



    }
}
