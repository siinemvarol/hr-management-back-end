package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GuestMailRegisterModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestMailRegisterConsumer {
    private final MailService mailService;

    @RabbitListener(queues =  "guest-mail-register-queue")
    public void sendGuestActivationMail(GuestMailRegisterModel guestMailRegisterModel){
        System.out.println(guestMailRegisterModel);
        mailService.sendGuestActivationMail(guestMailRegisterModel);
    }
}
