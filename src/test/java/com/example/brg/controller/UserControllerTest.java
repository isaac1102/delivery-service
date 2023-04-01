package com.example.brg.controller;

import com.example.brg.config.SecurityConfig;
import com.example.brg.domain.user.controller.UserController;
import com.example.brg.domain.user.controller.request.LoginRequest;
import com.example.brg.domain.user.controller.request.SignUpRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
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
    void signup_success() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test10", "aaaaaaaaaaB.1", "testo");

        mockMvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 글자수 미충족으로 인한 valid 통과 실패")
    @WithMockUser
    void signup_fail_invalid_password() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test1", "aabbbbB.1", "testo");

        mockMvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호 대소문자 규칙 미충족으로 인한 valid 통과 실패")
    @WithMockUser
    void signup_fail_invalid_password_character() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test1", "aabbbba.1", "testo");

        mockMvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호 특수문자 미포함으로 인한 valid 통과 실패")
    @WithMockUser
    void signup_fail_invalid_password_special_character() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test1", "aaaaaaaaaB31", "testo");

        mockMvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("가입자 id 길이 valid 통과 실패")
    @WithMockUser
    void signup_fail_by_name() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test", "aaaaaaaaaB31", "testo");

        mockMvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("기존 가입자는 회원가입 실패")
    @WithMockUser
    void signup_fail() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test10", "aaaaaaaaaaB.1", "testo");

        Mockito.doThrow(new UserException(UserErrorCode.ALREADY_EXISTING_ACCOUNT, "")).when(userService).createUser(any(), any(), any());

        mockMvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {
        String userId = "test1";
        String password = "aaaaaaaaaaaB.1";

        LoginRequest loginRequest = new LoginRequest(userId, password);

        when(userService.login(userId, password)).thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않는 경우 실패")
    @WithMockUser
    void login_fail() throws Exception {
        String userId = "test1";
        String password = "aaaaaaaaaaaB.1";

        LoginRequest loginRequest = new LoginRequest(userId, password);

        when(userService.login(userId, password)).thenThrow(new UserException(UserErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("비밀번호가 blank인 경우 예외발생")
    @WithMockUser
    void login_fail_password_is_blank() throws Exception {
        String userId = "test1";
        String password = "";

        LoginRequest loginRequest = new LoginRequest(userId, password);

        when(userService.login(userId, password)).thenThrow(new UserException(UserErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저아이디가 blank인 경우 예외발생")
    @WithMockUser
    void login_fail_userId_is_blank() throws Exception {
        String userId = "";
        String password = "aaaaaaaaaaB.1";

        LoginRequest loginRequest = new LoginRequest(userId, password);

        when(userService.login(userId, password)).thenThrow(new UserException(UserErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isBadRequest());
    }
}