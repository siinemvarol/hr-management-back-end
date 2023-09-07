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
    private String email;
    private String address;
    private String kurulusYili;
    private String vergiDairesi;
    private String vergiNo;
    private String logo;
    @Builder.Default
    private EStatus eStatus=EStatus.NOT_AUTHORIZED;

}
