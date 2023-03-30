package com.brg.delivery.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private static final String PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";

    @NotBlank(message = "id는 필수값입니다.")
    @Size(min = 5, max = 20, message = "id의 길이는 최소 5글자, 최대 20글자로 입력해 주세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "비밀번호는 영어 대소문자, 숫자, 특수문자 중 3가지 이상으로 구성된 12자 이상의 문자열이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(min = 2, max = 15, message = "이름의 길이는 최소 2글자, 최대 15글자로 입력해 주세요.")
    private String name;
}
