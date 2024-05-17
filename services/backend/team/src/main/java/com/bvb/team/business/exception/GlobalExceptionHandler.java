package com.bvb.team.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TeamAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(TeamAlreadyExistsException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(
                        ExceptionResponse.builder()
                                .error(ex.getReason())
                                .build()
                );
    }

    @ExceptionHandler(TeamDoesntExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(TeamDoesntExistsException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(
                        ExceptionResponse.builder()
                                .error(ex.getReason())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException ex) {
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error")
                                .error(ex.getMessage())
                                .build()
                );
    }
}
