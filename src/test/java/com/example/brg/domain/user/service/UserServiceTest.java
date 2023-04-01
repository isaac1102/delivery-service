package com.example.brg.domain.user.service;

import com.example.brg.domain.user.exception.UserErrorCode;
import com.example.brg.domain.user.exception.UserException;
import com.example.brg.domain.user.model.User;
import com.example.brg.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        encoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository, encoder);
    }

    @Test
    @DisplayName("기존 가입자가 가입을 시도하는 경우 예외가 발생한다.")
    void createUser() {
        // given
        String userId = "test1";
        String password = "password";
        String name = "Mr.Kim";
        User user = new User(userId, password, name);
        given(userRepository.findByUserId(userId)).willReturn(Optional.of(user));
        doThrow(new UserException(UserErrorCode.ALREADY_EXISTING_ACCOUNT, UserErrorCode.ALREADY_EXISTING_ACCOUNT.getMessage()))
                .when(userService).createUser(userId, password, name);

        // when
        userService.createUser(userId, password, name);

        // then

        verify(userService).createUser(any(String.class), any(String.class), any(String.class));

    }

    @Test
    void login() {
    }
}