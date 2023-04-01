package com.example.brg.domain.user.controller;

import com.example.brg.domain.user.controller.request.LoginRequest;
import com.example.brg.domain.user.controller.request.SignUpRequest;
import com.example.brg.domain.user.service.UserService;
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
        userService.createUser(request.getUserId(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest){
        String token = userService.login(loginRequest.getUserId(), loginRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
