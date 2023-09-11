package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Company extends BaseEntity{

    @Id
    private String id;
    private String name;
    private String phone;
    private String infoEmail;
    private String address;
    private String establishmentDate;
    private String city;
    private String taxId;
    private String logo;
    private Long revenue;
    private Long expense;
    private Long profit;
    private Long loss;
    private Long netIncome;
    @Builder.Default
    private EStatus eStatus=EStatus.NOT_AUTHORIZED;

}
