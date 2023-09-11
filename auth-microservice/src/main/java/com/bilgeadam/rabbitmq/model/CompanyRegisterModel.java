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

    private String companyId;
    private String companyName;
    private String companyInfoEmail;
    private String companyPhone;
    private String companyTaxId;
    private String companyAddress;
    private String companyCity;

}
