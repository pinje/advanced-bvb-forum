package com.bvb.authentication.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email required")
    @NotBlank(message = "Email required")
    private String email;

    @NotEmpty(message = "Password required")
    @NotBlank(message = "Password required")
    private String password;
}
