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
public class UserCreateEmployeeModel implements Serializable {
    private String companyId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String companyEmail;
    private String password;
    private String rePassword;
    private String activationLink;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;
    @Builder.Default
    private ERole role = ERole.EMPLOYEE;
}
