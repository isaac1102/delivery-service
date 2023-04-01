package com.example.brg.domain.user.service;

import com.example.brg.domain.user.model.User;
import com.example.brg.domain.user.exception.UserException;
import com.example.brg.domain.user.exception.UserErrorCode;
import com.example.brg.domain.user.repository.UserRepository;
import com.example.brg.utils.JwtUtil;
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

    public void createUser(String userId, String password, String name) {
        User user = new User(userId, encoder.encode(password), name);

        userRepository.findByUserId(user.getUserId()).ifPresent(found -> {
            throw new UserException(UserErrorCode.ALREADY_EXISTING_ACCOUNT,
                    UserErrorCode.ALREADY_EXISTING_ACCOUNT.getMessage());
        });

        userRepository.save(user);
    }

    public String login(String userId, String password) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND,
                        UserErrorCode.USER_NOT_FOUND.getMessage()));

        if (!encoder.matches(password, foundUser.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD,
                    UserErrorCode.INVALID_PASSWORD.getMessage());
        }

        return JwtUtil.createToken(foundUser.getUserId(), key, JwtUtil.expiredTimeMs);
    }
}
