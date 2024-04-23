package com.bvb.authentication;

import com.bvb.authentication.persistence.Role;
import com.bvb.authentication.persistence.RoleRepository;
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
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName_shouldFindRoleByName() {
        Role role = Role.builder().id(1L).name("MEMBER").build();
        roleRepository.save(role);

        Optional<Role> roleFound = roleRepository.findByName("MEMBER");
        assertTrue(roleFound.isPresent());
        assertEquals(role.getName(), roleFound.get().getName());
    }
}