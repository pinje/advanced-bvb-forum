package com.bvb.tournament.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TournamentAlreadyExistsException extends ResponseStatusException {
    public TournamentAlreadyExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Tournament already exists"
        );
    }
}
