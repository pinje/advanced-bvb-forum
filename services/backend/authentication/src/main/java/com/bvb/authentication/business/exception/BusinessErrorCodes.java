package com.bvb.authentication.business.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {
    INVALID_CREDENTIALS(302, HttpStatus.FORBIDDEN, "Invalid credentials"),
    DUPLICATE_USERNAME(303, HttpStatus.BAD_REQUEST, "Username already exists"),
    DUPLICATE_EMAIL(304, HttpStatus.BAD_REQUEST, "Email already exists")
    ;

    @Getter
    private final int code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
