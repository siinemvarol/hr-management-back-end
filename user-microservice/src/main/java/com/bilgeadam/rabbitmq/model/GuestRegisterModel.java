package com.bilgeadam.rabbitmq.model;

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
    private String surname;
    private String phone;
    private String address;
    private String personalEmail;
    private String password;
}
