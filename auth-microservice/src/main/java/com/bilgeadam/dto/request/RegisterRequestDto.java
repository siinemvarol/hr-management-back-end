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
public class RegisterRequestDto {
    @NotBlank
    private String userName;
    private String name;
    private String email;
    private String password;
    private String companyName;
    private String phoneNumber;
    private String vkn;
    private String address;

}
