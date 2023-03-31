package com.example.brg.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserException extends RuntimeException{
    private UserErrorCode errorCode;
    private String meesage;
}
