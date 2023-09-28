package com.bilgeadam.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCompanyEmployeesResponseModel implements Serializable {
    private Long authid;
    private String name;
    private String surname;
    private String companyEmail;
    private String personalEmail;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;
    private Long createdDate;
}
