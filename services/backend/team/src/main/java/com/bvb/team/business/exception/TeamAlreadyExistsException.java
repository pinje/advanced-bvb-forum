package com.bvb.team.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TeamAlreadyExistsException extends ResponseStatusException {
    public TeamAlreadyExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Team already exists"
        );
    }
}
