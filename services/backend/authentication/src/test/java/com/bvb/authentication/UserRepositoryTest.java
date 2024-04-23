package com.bvb.authentication;

import com.bvb.authentication.persistence.User;
import com.bvb.authentication.persistence.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_shouldFindUserByEmail() {
        User user = User.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        userRepository.save(user);

        Optional<User> userFound = userRepository.findByEmail(user.getEmail());
        assertNotNull(userFound);
        assertTrue(userFound.isPresent());
        assertEquals(user.getEmail(), userFound.get().getEmail());
        assertEquals(user.getUsername(), userFound.get().getUsername());
        assertEquals(user.getPassword(), userFound.get().getPassword());
    }

    @Test
    void findByEmail_shouldNotFindUserByEmail() {
        Optional<User> userFound = userRepository.findByEmail("user@gmail.com");
        assertFalse(userFound.isPresent());
    }

    @Test
    void existsByUsername() {
        User user = User.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername(user.getUsername());
        assertTrue(exists);
    }

    @Test
    void existsByUsername_userShouldNotExist() {
        boolean exists = userRepository.existsByUsername("user123");
        assertFalse(exists);
    }

    @Test
    void existsByEmail_userShouldExist() {
        User user = User.builder()
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail(user.getEmail());
        assertTrue(exists);
    }

    @Test
    void existsByEmail_userShouldNotExist() {
        boolean exists = userRepository.existsByEmail("user@gmail.com");
        assertFalse(exists);
    }
}