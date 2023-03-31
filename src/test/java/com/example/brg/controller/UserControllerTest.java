package com.example.brg.controller;

import com.example.brg.config.SecurityConfig;
import com.example.brg.domain.user.request.LoginRequest;
import com.example.brg.domain.user.request.SignUpRequest;
import com.example.brg.domain.user.exception.UserException;
import com.example.brg.domain.user.exception.UserErrorCode;
import com.example.brg.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SecurityConfig securityConfig;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join_success() throws Exception {
        String userId = "user1";
        String password = "ajajajajajA.1";
        String userName = "user name";

        mockMvc.perform(post("/api/v1/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new SignUpRequest(userId, password, userName))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    @WithMockUser
    void join_fail() throws Exception {
        String userId = "user1";
        String password = "ajajajajajA.1";
        String userName = "user name";

        Mockito.doThrow(new UserException(UserErrorCode.ALREADY_EXISTING_ACCOUNT, "")).when(userService).createUser(any(), any(), any());

        mockMvc.perform(post("/api/v1/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new SignUpRequest(userId, password, userName))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {
        when(userService.login("aaaaaa", "bbbbbbb")).thenReturn("token");

         mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new LoginRequest("aaaaaa","bbbbbbb"))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패")
    @WithMockUser
    void login_fail() throws Exception {
        when(userService.login("aa", "bb")).thenThrow(new UserException(UserErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new LoginRequest("aa","bb"))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}