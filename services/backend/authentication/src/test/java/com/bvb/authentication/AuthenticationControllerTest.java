package com.bvb.authentication;

import com.bvb.authentication.persistence.User;
import com.bvb.authentication.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    void registerUser_ShouldReturn202_WhenUserIsRegistered() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "email": "user@gmail.com",
                            "username": "user123",
                            "password": "12345678"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void registerUser_ShouldReturn400_WhenInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user",
                            "username": "user123",
                            "password": "12345678"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                     {
                         "validationErrors": [
                             "Email is not valid"
                         ]
                     }
                 """));
    }

    @Test
    void registerUser_ShouldReturn400_WhenInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user@gmail.com",
                            "username": "user123",
                            "password": "123"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                     {
                         "validationErrors": [
                             "Password too short, min. 8 characters"
                         ]
                     }
                 """));
    }

    @Test
    void registerUser_ShouldReturn400_WhenInvalidUsername() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user@gmail.com",
                            "username": "user123!@$%",
                            "password": "12345678"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                     {
                         "validationErrors": [
                             "Only letters and numbers are allowed"
                         ]
                     }
                 """));
    }

    @Test
    void registerUser_ShouldReturn400_WhenEmptyRequest() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "",
                            "username": "",
                            "password": ""
                        }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                     {
                         "validationErrors": [
                             "Password required",
                             "Username too short, min. 2 characters",
                             "Password too short, min. 8 characters",
                             "Email required",
                             "Username required"
                         ]
                     }
                 """));
    }

    @Test
    void loginUser_ShouldReturn200_WhenUserIsLoggedIn() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user@gmail.com",
                            "password": "12345678"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void loginUser_ShouldReturn400_WhenIncorrectCredentials() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        userRepository.save(user);

        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user@gmail.com",
                            "password": "123"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("""
                    {
                        "businessErrorDescription": "Internal error",
                        "error": "Bad credentials"
                    }
                """));
    }

    @Test
    void loginUser_ShouldReturn400_WhenInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user",
                            "password": "12345678"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "validationErrors": [
                            "Email is not valid"
                        ]
                    }
                """));
    }

    @Test
    void loginUser_ShouldReturn500_WhenInvalidPasswordFormat() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .username("user123")
                .password("12345678")
                .build();
        userRepository.save(user);

        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "email": "user@gmail.com",
                            "password": "123"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("""
                    {
                        "businessErrorDescription": "Internal error",
                        "error": "Bad credentials"
                    }
                """));
    }
}
