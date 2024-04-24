package com.bvb.authentication.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @Email(message = "Email: not valid")
    @NotEmpty(message = "Email: required")
    @NotBlank(message = "Email: required")
    @Length(max = 50, message = "Email: too long (max. 50 characters)")
    private String email;

    @NotEmpty(message = "Username: required")
    @NotBlank(message = "Username: required")
    @Length(min = 2, message = "Username: too short (min. 2 characters)")
    @Length(max = 20, message = "Username: too long (max. 20 characters)")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Username: only letters and numbers allowed")
    private String username;

    @NotEmpty(message = "Password: required")
    @NotBlank(message = "Password: required")
    @Length(min = 8, message = "Password: too short (min. 8 characters)")
    @Length(max = 100, message = "Password: too long (max. 100 characters)")
    private String password;
}
