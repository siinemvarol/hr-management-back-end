package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GuestMailRegisterModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class GuestMailRegisterConsumer {
    private final MailService mailService;

    @RabbitListener(queues =  "guest-mail-register-queue")
    public void sendGuestActivationMail(GuestMailRegisterModel guestMailRegisterModel)  {
        try {
            mailService.sendGuestActivationMail(guestMailRegisterModel);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
