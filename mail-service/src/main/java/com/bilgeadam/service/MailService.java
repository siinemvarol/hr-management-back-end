package com.bilgeadam.service;


import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.MailException;
import com.bilgeadam.rabbitmq.model.AddEmployeeMailModel;
import com.bilgeadam.rabbitmq.model.GuestMailRegisterModel;
import com.bilgeadam.rabbitmq.model.MailForgotPassModel;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private static String CONFIRMATION_URL = "http://localhost:9090/api/v1/auth/user-active?token=";
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(MailRegisterModel mailRegisterModel) throws MessagingException {
        CONFIRMATION_URL = CONFIRMATION_URL + mailRegisterModel.getActivationLink();
        String templateName = "authentication-email";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", mailRegisterModel.getUsername());
        CONFIRMATION_URL = String.format(CONFIRMATION_URL);
        properties.put("confirmationUrl", CONFIRMATION_URL);
        Context context = new Context();
        context.setVariables(properties);
        helper.setFrom("bouali.social@gmail.com");
        helper.setTo(mailRegisterModel.getCompanyEmail());
        helper.setSubject("Welcome to our nice platform");
        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
        System.out.println("mail gönderildi");
        return "başarılı";
    }


    public String sendMail(AddEmployeeMailModel mailModel) throws MessagingException {
        CONFIRMATION_URL = CONFIRMATION_URL + mailModel.getActivationLink();
        String templateName = "authentication-email";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", mailModel.getUsername());
        properties.put("companyEmail", mailModel.getCompanyEmail());
        CONFIRMATION_URL = String.format(CONFIRMATION_URL);
        properties.put("confirmationUrl", CONFIRMATION_URL);
        Context context = new Context();
        context.setVariables(properties);
        helper.setFrom("bouali.social@gmail.com");
        helper.setTo(mailModel.getPersonalEmail());
        helper.setSubject("Welcome to our Human Resources platform");
        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
        System.out.println("mail gönderildi");
        return "başarılı";
    }


    public void sendForgotPassword(MailForgotPassModel mailForgotPassModel) {
        try {
            System.out.println(mailForgotPassModel);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("your_email@example.com"); // Şirketinizi temsil eden e-posta adresi
            if (mailForgotPassModel.getCompanyEmail() != null) {
                mailMessage.setTo(mailForgotPassModel.getCompanyEmail());
            } else mailMessage.setTo(mailForgotPassModel.getPersonalEmail());
            mailMessage.setSubject("Sayın " + mailForgotPassModel.getUsername());
            mailMessage.setText("Yenilenen şifreniz aşağıda bulunmaktadır. \n\n Password: " + mailForgotPassModel.getRandomPassword());
            mailSender.send(mailMessage);
            System.out.println("Mail Gönderildi");
        } catch (MailException e) {
            throw new MailException(ErrorType.MAIL_SEND_ERROR);
        }
    }


    public String sendGuestActivationMail(GuestMailRegisterModel guestMailRegisterModel) throws MessagingException {
        CONFIRMATION_URL = CONFIRMATION_URL + guestMailRegisterModel.getActivationLink();
        System.out.println("guest mail register model" + guestMailRegisterModel);
        String templateName = "authentication-email";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", guestMailRegisterModel.getUsername());
        CONFIRMATION_URL = String.format(CONFIRMATION_URL);
        properties.put("confirmationUrl", CONFIRMATION_URL);
        Context context = new Context();
        context.setVariables(properties);
        helper.setTo(guestMailRegisterModel.getPersonalEmail());
        helper.setSubject("Welcome to our nice platform");
        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
        return "başarılı";
    }


}
