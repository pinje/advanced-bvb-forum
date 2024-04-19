package com.bvb.user.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<Object> handleRequestException(RequestException e) {
        return new ResponseEntity<>(e.getBody(), HttpStatus.BAD_REQUEST);
    }
}
