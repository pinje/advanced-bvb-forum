package com.bvb.user.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequestException extends ResponseStatusException {
    public RequestException(HttpStatus status, String message) {
        super(status, message);
    }
}
