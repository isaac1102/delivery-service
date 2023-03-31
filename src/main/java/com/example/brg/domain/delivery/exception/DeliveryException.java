package com.example.brg.domain.delivery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeliveryException extends RuntimeException{
    private DeliveryErrorCode errorCode;
    private String meesage;
}
