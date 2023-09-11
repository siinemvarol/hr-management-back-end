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
public class CompanyRegisterModel implements Serializable {

    private String id;
    private String companyName;
    private String infoEmail;
    private String companyPhone;
    private String taxId;
    private String companyAddress;
    private String city;

}
