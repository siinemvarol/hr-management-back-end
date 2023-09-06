package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.MailForgotPassModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailForgotPassProducer {

    private final String exchange = "auth-exchange";

    private String forgotPassMailBinding = "forgot-pass-mail-binding";

    private final RabbitTemplate rabbitTemplate;

    public void forgotPasswordSendMail(MailForgotPassModel mailForgotPassModel){
        rabbitTemplate.convertAndSend(exchange, forgotPassMailBinding, mailForgotPassModel);
    }
}
