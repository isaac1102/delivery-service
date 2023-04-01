package com.example.brg.domain.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "id는 필수값입니다.")
    private String userId;
    @NotBlank(message = "password는 필수값입니다.")
    private String password;
}
