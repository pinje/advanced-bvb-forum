package com.bvb.authentication.business;

import com.bvb.authentication.business.exception.EmailAlreadyExistsException;
import com.bvb.authentication.business.exception.UsernameAlreadyExistsException;
import com.bvb.authentication.config.security.JwtService;
import com.bvb.authentication.domain.AuthenticationRequest;
import com.bvb.authentication.domain.AuthenticationResponse;
import com.bvb.authentication.domain.AuthorizationResponse;
import com.bvb.authentication.domain.RegistrationRequest;
import com.bvb.authentication.persistence.RoleRepository;
import com.bvb.authentication.persistence.User;
import com.bvb.authentication.persistence.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequest request) {
        var memberRole = roleRepository.findByName("MEMBER")
                .orElseThrow(() -> new IllegalStateException("Role MEMBER not initialized"));

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(memberRole))
                .build();

        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("username", user.getUsername());
        claims.put("id", user.getName());
        var jwtToken = jwtService.generateToken(claims, user);

        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken)
                .httpOnly(true)
//                .secure(true)
                .path("/")
                .maxAge(604800)
//                .sameSite("Strict")
                .build();

        return AuthenticationResponse.builder()
                .jwtCookie(jwtCookie)
                .build();
    }

    public AuthorizationResponse getUserRole(HttpServletRequest request) {
        if (request.getCookies() == null || request.getCookies().length == 0) {
            return AuthorizationResponse.builder().userInfo("GUEST").build();
        }

        String token = request.getCookies()[0].getValue();
        String username = jwtService.extractRole(token);

        return AuthorizationResponse.builder().userInfo(username).build();
    }

    public AuthorizationResponse getUsername(HttpServletRequest request) {
        if (request.getCookies() == null || request.getCookies().length == 0) {
            return AuthorizationResponse.builder().userInfo("").build();
        }

        String token = request.getCookies()[0].getValue();
        String username = jwtService.extractUsername(token);

        return AuthorizationResponse.builder().userInfo(username).build();
    }

    public AuthorizationResponse getUserId(HttpServletRequest request) {
        if (request.getCookies() == null || request.getCookies().length == 0) {
            return AuthorizationResponse.builder().userInfo("").build();
        }

        String token = request.getCookies()[0].getValue();
        String userid = jwtService.extractUserId(token);

        return AuthorizationResponse.builder().userInfo(userid).build();
    }

    public AuthenticationResponse logout() {
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", "")
                .httpOnly(true)
//                .secure(true)
                .path("/")
                .maxAge(0)
//                .sameSite("Strict")
                .build();

        return AuthenticationResponse.builder()
                .jwtCookie(jwtCookie)
                .build();
    }
}
