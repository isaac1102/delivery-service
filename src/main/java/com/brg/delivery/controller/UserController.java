package com.brg.delivery.controller;

import com.brg.delivery.domain.dto.LoginRequest;
import com.brg.delivery.domain.dto.SignUpRequest;
import com.brg.delivery.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }
}
