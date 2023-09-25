package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UpdateCompanyInformationModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyInformationConsumer {
    private final UserService userService;
    @RabbitListener(queues = "update-company-information-queue")
    public String returnCompanyIdForUpdateInformation(UpdateCompanyInformationModel model){
        return userService.returnCompanyIdForUpdateInformation(model);
    }
}
