package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompanyValuationRequestDto {
    private Long revenue;
    private Long expense;
    private Long profit;
    private Long loss;
    private Long netIncome;
}
