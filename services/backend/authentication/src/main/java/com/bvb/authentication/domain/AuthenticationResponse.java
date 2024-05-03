package com.bvb.authentication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private ResponseCookie cookie;
}
