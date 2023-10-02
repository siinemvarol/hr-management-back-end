package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // User Service Error
    LOGIN_ERROR(4001, "Email or password is invalid", HttpStatus.BAD_REQUEST),
    BAD_REQUEST(4000,"", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4002,"User already exist" ,HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4003,"User not found" ,HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(4004,"Account is not active" ,HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4005, "Activation code error", HttpStatus.BAD_REQUEST),
    ALREADY_ACTIVE(4006, "User is already active", HttpStatus.BAD_GATEWAY),
    PASSWORD_ERROR(4007,"Passwords do not match", HttpStatus.BAD_REQUEST),
    USER_NOT_AUTHORIZED(4008,"User is not authorized to make comment for this company.", HttpStatus.BAD_REQUEST),

    // Service Error
    INTERNAL_ERROR(5000, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),;

    private int code;
    private String message;
    HttpStatus httpStatus;
}
