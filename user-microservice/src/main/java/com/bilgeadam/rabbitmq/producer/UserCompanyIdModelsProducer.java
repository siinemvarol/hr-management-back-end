package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UserAddEmployeeModel;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserCompanyListModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCompanyIdModelsProducer {
    private String exchange =  "user-exchange";
    private String userListCompanyBinding = "userList-company-binding";

    private final RabbitTemplate rabbitTemplate;
    public void sendUserList(List<UserCompanyIdModel> companyIdModels){
        System.out.println(companyIdModels + "producer");
        rabbitTemplate.convertAndSend(exchange, userListCompanyBinding, companyIdModels);
    }
}
