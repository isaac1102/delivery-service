package com.example.delivery.model;

import com.example.delivery.request.SignUpRequest;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String password;

    private String name;

    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public User(SignUpRequest request) {
        this(request.getId(), request.getPassword(), request.getName());
    }

    public boolean validatePassword() {
        int typeCount = 0;

        if (password.matches(".*[A-Z].*")) {
            typeCount++;
        }
        if (password.matches(".*[a-z].*")) {
            typeCount++;
        }
        if (password.matches(".*[0-9].*")) {
            typeCount++;
        }
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            typeCount++;
        }

        if (password == null || password.length() < 12 || typeCount < 4) {
            return false;
        }

        return true;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
