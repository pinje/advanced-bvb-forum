package com.bvb.user;

import com.bvb.user.persistence.RoleEnum;
import com.bvb.user.persistence.UserEntity;
import com.bvb.user.persistence.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_shouldSaveUserWithAllFields() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("user@gmail.com")
                .username("username")
                .password("123")
                .role(RoleEnum.MEMBER)
                .build();

        UserEntity savedUser = userRepository.save(user);

        assertNotNull(savedUser);


        UserEntity expectedUser = UserEntity.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .role(savedUser.getRole())
                .build();

        savedUser = entityManager.find(UserEntity.class, savedUser.getId());

        assertEquals(expectedUser, savedUser);

    }
}
