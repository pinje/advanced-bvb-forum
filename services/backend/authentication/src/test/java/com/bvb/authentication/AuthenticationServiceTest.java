package com.bvb.authentication;

import com.bvb.authentication.business.AuthenticationService;
import com.bvb.authentication.business.exception.EmailAlreadyExistsException;
import com.bvb.authentication.business.exception.UsernameAlreadyExistsException;
import com.bvb.authentication.config.security.JwtService;
import com.bvb.authentication.domain.AuthenticationRequest;
import com.bvb.authentication.domain.AuthenticationResponse;
import com.bvb.authentication.domain.RegistrationRequest;
import com.bvb.authentication.persistence.Role;
import com.bvb.authentication.persistence.RoleRepository;
import com.bvb.authentication.persistence.User;
import com.bvb.authentication.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;

    @Test
    void Add_ValidUser_UserSavedInRepository() {
        // ARRANGE
        Role member = Role.builder().id(1L).name("MEMBER").build();
        when(roleRepository.save(member)).thenReturn(member);

        RegistrationRequest request = RegistrationRequest.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(roleRepository.findByName("MEMBER")).thenReturn(Optional.of(member));

        // ACT
        roleRepository.save(member);
        authenticationService.register(request);

        // ASSERT
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(roleRepository, times(1)).findByName("MEMBER");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void Add_UserWithAlreadyExistingEmail_ThrowsException() throws EmailAlreadyExistsException {
        // ARRANGE
        Role member = Role.builder().id(1L).name("MEMBER").build();
        when(roleRepository.save(member)).thenReturn(member);

        RegistrationRequest request = RegistrationRequest.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        when(roleRepository.findByName("MEMBER")).thenReturn(Optional.of(member));

        // ACT
        roleRepository.save(member);
        ResponseStatusException exception = assertThrows(EmailAlreadyExistsException.class, () -> authenticationService.register(request));

        // ASSERT
        assertEquals("Email already exists", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
    }

    @Test
    void Add_UserWithAlreadyExistingUsername_ThrowsException() throws UsernameAlreadyExistsException {
        // ARRANGE
        Role member = Role.builder().id(1L).name("MEMBER").build();
        when(roleRepository.save(member)).thenReturn(member);

        RegistrationRequest request = RegistrationRequest.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);
        when(roleRepository.findByName("MEMBER")).thenReturn(Optional.of(member));

        // ACT
        roleRepository.save(member);
        ResponseStatusException exception = assertThrows(UsernameAlreadyExistsException.class, () -> authenticationService.register(request));

        // ASSERT
        assertEquals("Username already exists", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
    }

    @Test
    void Login_ValidUser_ReturnJwtToken() {
        // ARRANGE
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("12345678")
                .build();

        User user = User.builder()
                .id(1L)
                .email(request.getEmail())
                .username("user123")
                .password(request.getPassword())
                .build();
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(anyMap(), any(User.class))).thenReturn("token");

        // ACT
        AuthenticationResponse response = authenticationService.authenticate(request);

        // ASSERT
        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(anyMap(), any(User.class));
    }
}
