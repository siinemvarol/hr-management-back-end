package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRegisterRequestDto {

    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String companyName;
    private String phoneNumber;
    private String vkn;
    private String address;


}

