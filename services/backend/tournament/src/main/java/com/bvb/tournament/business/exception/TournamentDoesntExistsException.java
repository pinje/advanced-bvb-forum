package com.bvb.tournament.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TournamentDoesntExistsException extends ResponseStatusException {
    public TournamentDoesntExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Tournament doesn't exists"
        );
    }
}
