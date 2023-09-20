package com.bilgeadam.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailForgotPassModel implements Serializable {
    private String username;
    private String personalEmail;
    private String companyEmail;
    private String randomPassword;
}
