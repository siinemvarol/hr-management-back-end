package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(5000,"Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000,"", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4002,"User already exist" ,HttpStatus.BAD_REQUEST),
    MAIL_SEND_ERROR(1001,"Email send error",HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatus httpStatus;
}
