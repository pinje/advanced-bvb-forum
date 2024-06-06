package com.bvb.authentication;

import com.bvb.authentication.business.AuthenticationService;
import com.bvb.authentication.domain.AuthenticationRequest;
import com.bvb.authentication.domain.AuthenticationResponse;
import com.bvb.authentication.domain.AuthorizationResponse;
import com.bvb.authentication.domain.RegistrationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrationRequest request) {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request,
            HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, authenticationService.authenticate(request).getJwtCookie().toString());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/authorities")
    public ResponseEntity<AuthorizationResponse> authorities(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.getUserRole(request));
    }

    @PostMapping("/username")
    public ResponseEntity<AuthorizationResponse> username(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.getUsername(request));
    }

    @PostMapping("/userid")
    public ResponseEntity<AuthorizationResponse> userid(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.getUserId(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, authenticationService.logout().getJwtCookie().toString());
        return ResponseEntity.ok(authenticationService.logout());
    }
}
