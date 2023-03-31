package com.brg.delivery.service;

import com.brg.delivery.domain.User;
import com.brg.delivery.exception.AppException;
import com.brg.delivery.exception.ErrorCode;
import com.brg.delivery.repository.UserRepository;
import com.brg.delivery.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.secret}")
    private String key;
    private final Long expiredTimeMs = 1000 * 60 * 60L;

    public void createUser(String userId, String password, String name) {
        User user = new User(userId, encoder.encode(password), name);

        userRepository.findByUserId(user.getUserId()).ifPresent(found -> {
            throw new AppException(ErrorCode.ALREADY_EXISTING_ACCOUNT, "이미 존재하는 계정입니다.");
        });

        userRepository.save(user);
    }

    public String login(String userId, String password) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "계정을 찾을 수 없습니다."));

        if (!encoder.matches(password, foundUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
        }

        return JwtUtil.createToken(foundUser.getUserId(), key, expiredTimeMs);
    }
}
