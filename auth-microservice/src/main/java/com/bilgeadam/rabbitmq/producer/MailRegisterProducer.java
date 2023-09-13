package com.bilgeadam.rabbitmq.producer;


import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailRegisterProducer {
    private final RabbitTemplate rabbitTemplate;

    private String exchange = "auth-exchange";
    private String mailRegisterBinding = "mail-register-binding";

    public void sendMailRegister(MailRegisterModel mailRegisterModel) {
        System.out.println("auth mail producer : "+mailRegisterModel);
        rabbitTemplate.convertAndSend(exchange,
                mailRegisterBinding, mailRegisterModel);
    }
}

