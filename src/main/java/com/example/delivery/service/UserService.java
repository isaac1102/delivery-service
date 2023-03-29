package com.example.delivery.service;

import com.example.delivery.model.User;
import com.example.delivery.repository.UserRepository;
import com.example.delivery.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(SignUpRequest request) {
        User user = new User(request);

        if (userRepository.findById(user.getId()).isPresent()){
            throw new DuplicateKeyException("이미 존재하는 계정입니다.");
        }

        userRepository.save(user);
    }
}
