package com.brg.delivery.service;

import com.brg.delivery.domain.User;
import com.brg.delivery.domain.dto.LoginRequest;
import com.brg.delivery.exception.AppException;
import com.brg.delivery.exception.ErrorCode;
import com.brg.delivery.repository.UserRepository;
import com.brg.delivery.domain.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public void createUser(SignUpRequest request) {
        User user = new User(request.getUserId(), encoder.encode(request.getPassword()), request.getName());

        userRepository.findByUserId(user.getUserId()).ifPresent(found -> {
            throw new AppException(ErrorCode.ALREADY_EXISTING_ACCOUNT, "이미 존재하는 계정입니다.");
        });

        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        return "";
    }

    public String login(String s, String s1) {
        return "";
    }
}
