package com.ecommerce.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EcommerceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getFieldErrors().forEach(error -> {
            errors.put(error.getField(),new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST).getBody());
        });
        return errors;
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException nullPointerException) {
       return new ResponseEntity<>(nullPointerException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
