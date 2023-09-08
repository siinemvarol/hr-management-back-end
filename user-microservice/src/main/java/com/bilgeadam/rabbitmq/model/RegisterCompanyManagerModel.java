package com.bilgeadam.rabbitmq.model;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCompanyManagerModel implements Serializable {

    private String companyId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String companyEmail;
    private String password;

    @Builder.Default
    private EStatus eStatus=EStatus.PENDING;
    @Builder.Default
    private ERole eRole=ERole.EMPLOYEE;

}
