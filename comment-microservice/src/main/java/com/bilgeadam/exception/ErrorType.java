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
    INTERNAL_ERROR(5000, "Server error", HttpStatus.INTERNAL_SERVER_ERROR),
    COMPANY_NOT_FOUND(4003,"Company not found" ,HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
