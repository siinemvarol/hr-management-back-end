package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetCompanyEmployeesCompanyIdModel;
import com.bilgeadam.rabbitmq.model.GetCompanyEmployeesResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCompanyEmployeesCompanyIdProducer {
    private String exchange = "company-exchange";
    private final String getCompanyEmployeesCompanyIdBinding = "get-company-employees-company-id-binding";
    private final RabbitTemplate rabbitTemplate;
    public List<GetCompanyEmployeesResponseModel> sendAuthIdGetEmployees(GetCompanyEmployeesCompanyIdModel model){
        return (List<GetCompanyEmployeesResponseModel>) rabbitTemplate.convertSendAndReceive(exchange, getCompanyEmployeesCompanyIdBinding, model);
    }

}
