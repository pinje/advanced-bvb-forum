package com.bvb.user.business;

import com.bvb.user.business.exception.EmailAlreadyExistsException;
import com.bvb.user.business.exception.RequestException;
import com.bvb.user.business.exception.UsernameAlreadyExistsException;
import com.bvb.user.persistence.RoleEnum;
import com.bvb.user.domain.CreateUserRequest;
import com.bvb.user.domain.CreateUserResponse;
import com.bvb.user.persistence.UserEntity;
import com.bvb.user.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        UserEntity saveUser = save(request);

        return CreateUserResponse.builder()
                .userId(saveUser.getId())
                .build();
    }

    private UserEntity save(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newUser = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encodedPassword)
                .role(RoleEnum.MEMBER)
                .build();

        return userRepository.save(newUser);
    }
}
