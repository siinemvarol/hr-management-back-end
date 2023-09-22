package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetCompanyInformationManagerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyInformationManagerProducer {
    private String exchange = "company-exchange";
    private final String getCompanyInformationManagerBinding = "get-company-information-manager-binding";
    private final RabbitTemplate rabbitTemplate;
    public String returnCompanyIdManager(GetCompanyInformationManagerModel getCompanyInformationManagerModel){
        return rabbitTemplate.convertSendAndReceive(exchange, getCompanyInformationManagerBinding, getCompanyInformationManagerModel).toString();
    }

}
