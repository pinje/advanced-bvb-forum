package com.bvb.authentication;

import com.bvb.authentication.business.AuthenticationService;
import com.bvb.authentication.domain.AuthenticationRequest;
import com.bvb.authentication.domain.AuthenticationResponse;
import com.bvb.authentication.persistence.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;

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
                             "Email: not valid"
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
                             "Password: too short (min. 8 characters)"
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
                             "Username: only letters and numbers allowed"
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
                             "Password: required",
                             "Username: too short (min. 2 characters)",
                             "Password: too short (min. 8 characters)",
                             "Email: required",
                             "Username: required"
                         ]
                     }
                 """));
    }

    @Test
    void loginUser_ShouldReturn200_WhenUserIsLoggedIn() throws Exception {

        ResponseCookie responseCookie = ResponseCookie.from("jwtToken", "token")
                .httpOnly(true)
                .path("/")
                .maxAge(604800)
                .sameSite("Strict")
                .build();

        AuthenticationResponse response = AuthenticationResponse.builder()
                        .jwtCookie(responseCookie)
                        .build();

        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

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

        verify(authenticationService, times(2)).authenticate(any(AuthenticationRequest.class));
    }

    @Test
    void loginUser_ShouldReturn400_WhenIncorrectCredentials() throws Exception {
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenThrow(new RuntimeException("Bad credentials"));
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

        verify(authenticationService, times(1)).authenticate(any(AuthenticationRequest.class));
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
}
