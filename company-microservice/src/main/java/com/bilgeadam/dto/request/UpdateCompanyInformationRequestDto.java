package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompanyInformationRequestDto {
    private String companyName;
    private String companyPhone;
    private String infoEmail;
    private String companyAddress;
    private String establishmentDate;
    private String city;
    private String taxId;
}
