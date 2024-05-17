package com.bvb.match.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MatchAlreadyExistsException extends ResponseStatusException {
    public MatchAlreadyExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Match already exists"
        );
    }
}
