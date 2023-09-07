package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyUpdateRequestDto {
    private String companyId;
    private String name;
    private String surname;
    private String email;
    private String companyName;
    private String city;
    private String phone;

}
