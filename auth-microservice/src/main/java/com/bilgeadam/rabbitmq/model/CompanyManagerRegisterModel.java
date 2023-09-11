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
public class CompanyManagerRegisterModel implements Serializable {
    private String companyManagerName;
    private String companyManagerSurname;
    private String companyManagerCompanyEmail;
    private String companyManagerPhone;
    private String companyManagerPassword;
}
