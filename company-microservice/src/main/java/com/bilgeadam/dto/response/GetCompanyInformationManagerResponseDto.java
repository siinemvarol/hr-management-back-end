package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCompanyInformationManagerResponseDto {
    private String companyName;
    private String infoEmail;
    private String companyPhone;
    private String taxId;
    private String companyAddress;
    private String city;
    private String establishmentDate;

}
