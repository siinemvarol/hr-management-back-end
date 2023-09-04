package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User extends BaseEntity {

    @Id
    private String id;
    private Long authid;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String info;
    private String avatar;
    private String birthday;
    @Builder.Default
    private EStatus eStatus=EStatus.PENDING;

}
