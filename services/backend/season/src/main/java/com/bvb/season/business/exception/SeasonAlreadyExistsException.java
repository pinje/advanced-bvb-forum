package com.bvb.season.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SeasonAlreadyExistsException extends ResponseStatusException {
    public SeasonAlreadyExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Season already exists"
        );
    }
}
