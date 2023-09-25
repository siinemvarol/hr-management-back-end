package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UpdateCompanyValuationModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyValuationConsumer {
    private final UserService userService;
    @RabbitListener(queues = "update-company-valuation-queue")
    public String returnCompanyIdForUpdateValuation(UpdateCompanyValuationModel model){
        return userService.returnCompanyIdForUpdateValuation(model);
    }
}
