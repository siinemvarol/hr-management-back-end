package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetCompanyInformationModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyInformationConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-company-information-queue")
    public String returnCompanyId(GetCompanyInformationModel getCompanyInformationModel){
        return userService.returnCompanyId(getCompanyInformationModel);
    }
}
