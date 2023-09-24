package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetCompanyValuationManagerModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyValuationManagerConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-company-valuation-manager-queue")
    public String returnCompanyIdManagerValuation(GetCompanyValuationManagerModel getCompanyValuationManagerModel){
        return userService.returnCompanyIdManagerValuation(getCompanyValuationManagerModel);
    }
}
