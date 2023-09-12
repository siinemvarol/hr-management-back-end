package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestRegisterRequestDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    private String personalEmail;
    private String password;

}
