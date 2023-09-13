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
public class AddEmployeeCompanyModel  implements Serializable {
    private String companyId;
    private String name;
    private String surname;
    private String username;
    private String personalEmail;
    private String companyEmail;
    private String password;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;
    @Builder.Default
    private ERole role = ERole.EMPLOYEE;
    @Builder.Default
    private EStatus status = EStatus.PENDING;
}
