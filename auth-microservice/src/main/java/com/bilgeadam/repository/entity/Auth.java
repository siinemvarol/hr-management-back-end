package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "tblauth")
public class Auth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String personalEmail;
    private String companyEmail;
    private String password;
    private String activationLink;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status=EStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private ERole role;


}
