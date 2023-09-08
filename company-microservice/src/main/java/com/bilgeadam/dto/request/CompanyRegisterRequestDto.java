package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRegisterRequestDto {

    @NotEmpty(message = "This field is required and cannot be empty")
    private String name;
    @NotEmpty(message = "This field is required and cannot be empty")
    private String surname;
    @Email(message = "Please enter a valid email address.")
    private String email;
    @NotEmpty(message = "This field is required and cannot be empty")
    private String phone;

}

