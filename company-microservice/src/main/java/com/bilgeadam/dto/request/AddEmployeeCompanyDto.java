package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddEmployeeCompanyDto {
    private String companyId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String rePassword;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;

}
