package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddEmployeeMailModel;
import com.bilgeadam.rabbitmq.model.MailForgotPassModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeMailConsumer {
    private final MailService mailService;

    @RabbitListener(queues =  "add-employee-mail-queue")
    public void sendMail(AddEmployeeMailModel mailModel){
        System.out.println("mail forgot consumer"+mailModel);
        try {
            mailService.sendMail(mailModel);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
