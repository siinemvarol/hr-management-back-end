package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetCompanyInformationManagerModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyInformationManagerConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-company-information-manager-queue")
    public String returnCompanyIdManager(GetCompanyInformationManagerModel getCompanyInformationManagerModel){
        return userService.returnCompanyIdManager(getCompanyInformationManagerModel);
    }
}
