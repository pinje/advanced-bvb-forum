package com.bvb.authentication;

import com.bvb.authentication.persistence.Role;
import com.bvb.authentication.persistence.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("MEMBER").isEmpty()) {
				roleRepository.save(
						Role.builder().name("MEMBER").build()
				);
			}
		};
	}

}
