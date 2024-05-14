package com.bvb.season.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SeasonDoesntExistsException extends ResponseStatusException {
    public SeasonDoesntExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Season doesn't exists"
        );
    }
}
