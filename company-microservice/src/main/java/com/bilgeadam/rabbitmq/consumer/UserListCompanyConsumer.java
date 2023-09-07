package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserCompanyListModel;
import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserListCompanyConsumer {

    private final CompanyService companyService;

    @RabbitListener(queues = "userList-company-queue")
    public void userListCompany(List<UserCompanyListModel> model){
        System.out.println(model + "consumer");
        companyService.userListCompany(model);
    }
}

