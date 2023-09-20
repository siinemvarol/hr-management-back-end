package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestRegisterRequestDto {

    @NotEmpty(message = "Name field cannot be empty")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;

    @NotEmpty(message = "Surname field cannot be empty")
    @Size(min = 3, max = 20, message = "Surname must be between 3 and 20 characters.")
    private String surname;

    @NotEmpty(message = "Phone field cannot be empty" )
    private String phoneNumber;

    @NotEmpty(message = "Address field cannot be empty" )
    private String address;

    @Email(message = "Please enter a valid email address.")
    private String personalEmail;

    @NotEmpty(message = "Password field cannot be empty")
    private String password;

}
