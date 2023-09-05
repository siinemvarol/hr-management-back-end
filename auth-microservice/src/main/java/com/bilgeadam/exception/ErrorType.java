package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //Auth Service ERROR
    VALIDATION_ERROR(3000,"",HttpStatus.BAD_REQUEST),
    BAD_REQUEST(4000,"", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4002,"User already exist" ,HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4003,"User not found" ,HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(4004,"Account is not active" ,HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5000,"Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORDS_NOT_MATCH(4007,"Passwords do not match",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
