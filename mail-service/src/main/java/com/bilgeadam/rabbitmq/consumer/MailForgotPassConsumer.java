package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.MailForgotPassModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailForgotPassConsumer {
    private final MailService mailService;

    @RabbitListener(queues =  "mail-forgot-password-queue")
    public void sendForgotPassword(MailForgotPassModel mailForgotPassModel){
        mailService.sendForgotPassword(mailForgotPassModel);
    }
}
