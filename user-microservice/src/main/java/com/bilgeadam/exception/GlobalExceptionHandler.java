package com.bilgeadam.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserManagerException.class)
    public ResponseEntity<ErrorMessage> handleManagerException(UserManagerException exception){
            ErrorType errorType = exception.getErrorType();
        HttpStatus httpStatus = errorType.httpStatus;
        return new ResponseEntity<>(createError(errorType, exception), httpStatus);
    }
    private ErrorMessage createError(ErrorType errorType, Exception exception){
        return ErrorMessage
                .builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleAllExceptions(Exception exception) {
        ErrorType errorType = ErrorType.INTERNAL_ERROR;
        List<String> fields = new ArrayList<>();
        fields.add(exception.getMessage());
        ErrorMessage errorMessage = createError(errorType, exception);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.ok("Beklenmeyen bir hata olustu: " + ex.getMessage());
    }

    // Istek atilirken arka tarafta belirlenen property'lerin validasyon kontrollerini dikkate alir.
    // Asagidaki metod bu validasyonlarin tipini ve mesajini dondurmek uzere tasarlanmistir.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        List<String> fields = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createError(errorType, exception);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }

    // Http isteginin veya yanitinin okunamamasi durumunda firlatilan hatadir
    // JSON formatta bir veri gonderirken bu formattaki bir yanlislik sonucunda (orn. fazla virgul kalmasi,
    // bir degerin eksik yazilmasi vb.) durumlarinda firlatilan hatadir.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorMessage> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }

    // Date date; --> mm.dd.yy
    // DateTime --> dd.mm.yyyy
    // Veri donusumu sirasinda firlatilan hatadir.
    // Bir parametre Date formatinda veri beklerken, String veya Integer tipinde bir veri
    // gonderildiginde veri donusumu olmayacagi icin bu hata firlatilir.
    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<ErrorMessage> handleInvalidFormatException(InvalidFormatException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }

    // Ikı parametrenin tiplerinin birbiriyle uyusmamasindan ortaya cikan hatadir
    // String tutulan bir parametrenin(degisken) Integer olarak gonderilmesi
    // sirasinda firlatilan hatadir.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(MethodArgumentTypeMismatchException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }

    // @PathVariable eksik oldugunda firlatilacak exception
    // @PathVariable -> auth/find-by-id/{id}
    @ExceptionHandler(MissingPathVariableException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(MissingPathVariableException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }

    // Bir veritabaninda unique olan bir sutunda ayni degerden birden fazla var ise firlatilacak hatadir.
    // Yani username property'si veritabanina unique olarak isaretlendiyse ve bir degere(orn. java8) sahipse
    // bu degerden bir tane daha olmamalidir. Eger siz el ile bu degerden bir tane daha eklerseniz, veriyi
    // cekmek istediginizde DataIntegrityViolation hatasi alirsiniz.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorMessage> handlePsqlException(DataIntegrityViolationException exception) {
        ErrorType errorType = ErrorType.USERNAME_DUPLICATE;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }
}
