package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UpdateCompanyInformationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyInformationProducer {
    private String exchange = "company-exchange";
    private final String updateCompanyInformationBinding = "update-company-information-binding";
    private final RabbitTemplate rabbitTemplate;
    public String returnCompanyIdForUpdateInformation(UpdateCompanyInformationModel model){
        return rabbitTemplate.convertSendAndReceive(exchange, updateCompanyInformationBinding, model).toString();
    }
}
