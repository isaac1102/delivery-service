package com.example.brg.domain.delivery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DeliveryErrorCode {
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "종료일이 시작일보다 앞설 수 없습니다."),
    EXEED_MAX_DATE_RANGE(HttpStatus.BAD_REQUEST, "최대 조회가능 일수(3일)를 초과하셨습니다."),
    NOT_UPDATABLE_STATUS(HttpStatus.BAD_REQUEST, "변경가능한 상태가 아닙니다."),
    NOT_FOUND_DELIVERY(HttpStatus.NOT_FOUND, "배달내역을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}

