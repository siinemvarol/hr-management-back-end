package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.MailForgotPassModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailForgotPasswordProducer {
    private final RabbitTemplate rabbitTemplate;

    private String exchange = "auth-exchange";
    private String mailRegisterBinding = "mail-forgot-password-binding";

    public void forgotPasswordSendMail(MailForgotPassModel mailForgotPassModel) {
        rabbitTemplate.convertAndSend(exchange,
                mailRegisterBinding, mailForgotPassModel);
    }
}
