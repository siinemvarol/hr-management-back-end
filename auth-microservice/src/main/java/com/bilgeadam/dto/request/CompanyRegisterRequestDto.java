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

    // company manager (user)
    private String name;
    private String surname;
    private String companyEmail;
    private String phone;
    private String password;

    // company
    private String companyName;
    private String infoEmail;
    private String companyPhone;
    private String taxId;
    private String companyAddress;
    private String city;


}
