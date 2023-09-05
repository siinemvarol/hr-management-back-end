package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
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
    private String userId;
    private String name;
    private String surname;
    private String email;
    private String companyName;
    private String city;
    private String phone;
    private String taxIdNumber;
    @Builder.Default
    private EStatus eStatus=EStatus.NOT_AUTHORIZED;
    @Builder.Default
    private ERole eRole=ERole.COMPANY_MANAGER;
}
