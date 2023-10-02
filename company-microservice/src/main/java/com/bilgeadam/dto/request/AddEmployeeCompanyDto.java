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
public class AddEmployeeCompanyDto {
    private Long authid;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    @Size(min = 3, max = 20, message = "Surname must be between 3 and 20 characters.")
    private String surname;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    @Email(message = "Please enter a valid email address.")
    private String personalEmail;

    @NotEmpty
    @Pattern(message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,32}$")
    private String password;

    @NotEmpty(message = "Phone cannot be empty" )
    private String phone;

    @NotEmpty(message = "Address cannot be empty" )
    private String address;

    private String info;

    private String avatar;

    @NotEmpty(message = "Birthday cannot be empty" )
    private String birthday;
}
