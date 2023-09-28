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
public class MailRegisterModel implements Serializable {
    private String activationLink;
    private String name;
    private String username;
    private String password;
    private String personalEmail;
    private String companyEmail;
}
