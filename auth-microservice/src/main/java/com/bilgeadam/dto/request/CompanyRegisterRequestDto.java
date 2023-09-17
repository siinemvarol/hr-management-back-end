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
public class CompanyRegisterRequestDto {

    // company manager (user)
    @NotEmpty(message = "Name field cannot be empty")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;

    @NotEmpty(message = "Surname field cannot be empty")
    @Size(min = 3, max = 20, message = "Surname must be between 3 and 20 characters.")
    private String surname;

    @Email(message = "Please enter a valid email address.")
    private String companyEmail;

    @NotEmpty(message = "Phone field cannot be empty" )
    private String phone;

    @NotEmpty(message = "Password field cannot be empty")
    @Size(min = 8,max = 64,message = "Password must be between 8-64 characters")
    @Pattern(message = "Password must be at least 8 characters and contain at least one uppercase, lowercase letter and number.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])")
    private String password;

    // company
    @NotEmpty(message = "Company name field cannot be empty")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String companyName;

    @Email(message = "Please enter a valid email address.")
    private String infoEmail;

    @NotEmpty(message = "Phone field cannot be empty" )
    private String companyPhone;

    @NotEmpty(message = "Tax id field cannot be empty" )
    @Digits(message=" Tax number must be 10 digits.", fraction = 0, integer = 10)
    private String taxId;

    @NotEmpty(message = "Company address field cannot be empty" )
    private String companyAddress;

    @NotEmpty(message = "City field cannot be empty" )
    private String city;

}
