package com.bilgeadam.rabbitmq.model;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestRegisterModel implements Serializable {
    private String name;
    private Long authid;
    private String surname;
    private String phone;
    private String address;
    private String personalEmail;
    private String password;
    private ERole role;
}
