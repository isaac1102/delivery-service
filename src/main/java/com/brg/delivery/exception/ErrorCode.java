package com.brg.delivery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_EXISTING_ACCOUNT(HttpStatus.CONFLICT, "");

    private HttpStatus status;
    private String message;
}

