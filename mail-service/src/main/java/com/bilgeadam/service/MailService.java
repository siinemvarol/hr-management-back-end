package com.bilgeadam.service;


import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.MailException;
import com.bilgeadam.rabbitmq.model.MailForgotPassModel;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {


    private final JavaMailSender mailSender;


    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendMail(MailRegisterModel mailRegisterModel) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("${spring.mail.username}"); // Şirketinizi temsil eden e-posta adresi
           mailMessage.setTo(mailRegisterModel.getPersonalEmail());

            mailMessage.setTo(mailRegisterModel.getPersonalEmail());
            mailMessage.setSubject("Username: " + mailRegisterModel.getUsername());
            mailMessage.setSubject("Password: " + mailRegisterModel.getPassword());



            mailMessage.setText("Merhaba, Neredeyse işleminiz tamamlandı.  \n\n"
                    +"Username:      "+ mailRegisterModel.getUsername()+"\n\n"
                    +"Password:      " + mailRegisterModel.getPassword()+"\n\n"
                    +"Company Email: " +mailRegisterModel.getCompanyEmail()+"\n\n"
                    + "\n\nLütfen Aktivasyon kodunu giriniz...\n\nAktivasyon Linkiniz:   "
                    + "http://localhost:9090/api/v1/auth/user-active?token="
                    + mailRegisterModel.getActivationLink());

            mailSender.send(mailMessage);
            System.out.println("Mail Gönderildi");
            return "Başarılı";
        } catch (MailException e) {
            throw new MailException(ErrorType.MAIL_SEND_ERROR);
        }

    }

    public void sendForgotPassword(MailForgotPassModel mailForgotPassModel) {
        try {
            System.out.println(mailForgotPassModel);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("your_email@example.com"); // Şirketinizi temsil eden e-posta adresi
            mailMessage.setTo(mailForgotPassModel.getPersonalEmail());
            mailMessage.setSubject("Sayın " + mailForgotPassModel.getUsername());
            mailMessage.setText("Yenilenen şifreniz aşağıda bulunmaktadır. \n\n Password: " + mailForgotPassModel.getRandomPassword());
            mailSender.send(mailMessage);
            System.out.println("Mail Gönderildi");
//
        } catch (MailException e) {
            throw new MailException(ErrorType.MAIL_SEND_ERROR);
        }
    }
}
