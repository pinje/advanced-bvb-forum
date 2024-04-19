package com.bvb.user;

import com.bvb.user.business.CreateUserUseCaseImpl;
import com.bvb.user.business.exception.EmailAlreadyExistsException;
import com.bvb.user.business.exception.UsernameAlreadyExistsException;
import com.bvb.user.domain.CreateUserRequest;
import com.bvb.user.domain.CreateUserResponse;
import com.bvb.user.persistence.RoleEnum;
import com.bvb.user.persistence.UserEntity;
import com.bvb.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void Add_ValidUser_UserSavedInRepository() {
        // ARRANGE
        CreateUserRequest request = CreateUserRequest.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("123")
                .build();
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .email(request.getEmail())
                .username(request.getUsername())
                .password("encoded_password")
                .role(RoleEnum.MEMBER)
                .build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");

        // ACT
        CreateUserResponse response = createUserUseCase.createUser(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void Add_UserWithAlreadyExistingEmail_ThrowsException() throws EmailAlreadyExistsException {
        // ARRANGE
        CreateUserRequest request = CreateUserRequest.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("123")
                .build();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // ACT
        ResponseStatusException exception = assertThrows(EmailAlreadyExistsException.class, () -> createUserUseCase.createUser(request));

        // ASSERT
        assertEquals("EMAIL_ALREADY_EXISTS", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
    }

    @Test
    void Add_UserWithAlreadyExistingUsername_ThrowsException() throws UsernameAlreadyExistsException {
        // ARRANGE
        CreateUserRequest request = CreateUserRequest.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("123")
                .build();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // ACT
        ResponseStatusException exception = assertThrows(UsernameAlreadyExistsException.class, () -> createUserUseCase.createUser(request));

        // ASSERT
        assertEquals("USERNAME_ALREADY_EXISTS", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
    }
}