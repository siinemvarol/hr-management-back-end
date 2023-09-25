package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UpdateCompanyValuationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyValuationProducer {
    private String exchange = "company-exchange";
    private final String updateCompanyValuationBinding = "update-company-valuation-binding";
    private final RabbitTemplate rabbitTemplate;
    public String returnCompanyIdForUpdateValuation(UpdateCompanyValuationModel model){
        return rabbitTemplate.convertSendAndReceive(exchange, updateCompanyValuationBinding, model).toString();
    }
}
