package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthLoginRequestDto {

    @NotEmpty
    @Email(message = "Please enter a valid email address.")
    private String companyEmail;

    @NotEmpty(message = "Password field cannot be empty")
    private String password;
}
