package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetCompanyValuationManagerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyValuationManagerProducer {
    private String exchange = "company-exchange";
    private final String getCompanyValuationManagerBinding = "get-company-valuation-manager-binding";
    private final RabbitTemplate rabbitTemplate;
    public String returnCompanyIdManagerValuation(GetCompanyValuationManagerModel getCompanyValuationManagerModel){
        return rabbitTemplate.convertSendAndReceive(exchange, getCompanyValuationManagerBinding, getCompanyValuationManagerModel).toString();
    }
}
