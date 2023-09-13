package com.bilgeadam.rabbitmq.model;

import com.bilgeadam.repository.enums.ERole;
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
    private String name;
    private String surname;
    private String companyEmail;
    private String phone;
    private String password;
    private ERole eRole;
}
