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
public class UserCompanyRegisterModel implements Serializable {
    private String companyId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    @Builder.Default
    private ERole eRole = ERole.COMPANY_MANAGER;
}
