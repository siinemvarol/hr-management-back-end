package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetCompanyInformationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyInformationProducer {
    private String exchange = "company-exchange";
    private final String getCompanyInformationBinding = "get-company-information-binding";
    private final RabbitTemplate rabbitTemplate;
    public String sendAuthIdToUser(GetCompanyInformationModel getCompanyInformationModel){
        return rabbitTemplate.convertSendAndReceive(exchange, getCompanyInformationBinding, getCompanyInformationModel).toString();
    }
}
