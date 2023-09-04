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
public class AuthRegisterRequestDto {
    // Username field with validation
    @NotEmpty(message = "Username is required.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    // Name field with validation
    @NotEmpty(message = "Name is required.")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;

    // Surname field with validation
    @NotEmpty(message = "Surname is required.")
    @Size(min = 3, max = 20, message = "Surname must be between 3 and 20 characters.")
    private String surname;

    // Email field with email validation
    @Email(message = "Please enter a valid email address.")
    private String email;

    // Password field with validation for minimum length, uppercase, lowercase, digits, and special characters
    @NotEmpty
    @Pattern(message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,32}$")
    private String password;

    // Re-entered password field (typically used for confirming the password)
    private String rePassword;
}
