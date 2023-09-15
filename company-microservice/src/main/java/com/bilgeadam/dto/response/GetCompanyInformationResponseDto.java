package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCompanyInformationResponseDto {
    private String companyName;
    private String companyPhone;
    private String infoEmail;
    private String companyAddress;
    private String establishmentDate;
    private String city;
    private String taxId;
    private String logo;
    private Long revenue;
    private Long expense;
    private Long profit;
    private Long loss;
    private Long netIncome;
}
