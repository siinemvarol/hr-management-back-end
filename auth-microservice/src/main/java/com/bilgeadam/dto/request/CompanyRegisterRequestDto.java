package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRegisterRequestDto {

    private String companyManagerName;
    private String companyManagerSurname;
    private String companyEmail;
    private String companyManagerPhone;
    private String password;
    private String companyName;
    private String companyInfoEmail;
    private String companyPhone;
    private String companyTaxId;
    private String companyAddress;
    private String companyCity;


}
