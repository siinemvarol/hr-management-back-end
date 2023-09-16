package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForgotPasswordRequestDto {

    @NotEmpty(message = "Id field cannot be empty")
    private Long authid;

    @NotEmpty(message = "Password field cannot be empty")
    @Size(min = 8,max = 64,message = "Password must be between 8-64 characters")
    @Pattern(message = "Password must be at least 8 characters and contain at least one uppercase, lowercase letter and number.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$")
    private String password;
}
