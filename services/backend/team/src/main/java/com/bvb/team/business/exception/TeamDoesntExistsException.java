package com.bvb.team.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TeamDoesntExistsException  extends ResponseStatusException {
    public TeamDoesntExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                "Team doesn't exists"
        );
    }
}
