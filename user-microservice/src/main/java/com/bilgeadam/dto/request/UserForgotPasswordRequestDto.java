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
    private String password;
}
