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
public class AddEmployeeSaveUserModel implements Serializable {
    private String username;
    private String personalEmail;
    private String password;
    private String companyEmail;
    private ERole role;
    private Long authid;
    private String companyId;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;

}
