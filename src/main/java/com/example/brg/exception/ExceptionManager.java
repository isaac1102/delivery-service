package com.example.brg.exception;

import com.example.brg.domain.delivery.exception.DeliveryException;
import com.example.brg.domain.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<?> deliveryException(DeliveryException ex){
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(ex.getMeesage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userException(UserException ex){
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(ex.getMeesage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}

