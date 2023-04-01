package com.example.brg.domain.delivery.controller.request;

import com.example.brg.domain.delivery.exception.DeliveryErrorCode;
import com.example.brg.domain.delivery.exception.DeliveryException;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class DeliveryRequest {
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    LocalDate endDate;

    public DeliveryRequest(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;

        isValidDate();
    }

    public void isValidDate() {
        if (endDate.isBefore(startDate)) {
            throw new DeliveryException(DeliveryErrorCode.INVALID_DATE_RANGE,
                    DeliveryErrorCode.INVALID_DATE_RANGE.getMessage());
        }

        if (startDate.plusDays(3).isBefore(endDate)) {
            throw new DeliveryException(DeliveryErrorCode.EXEED_MAX_DATE_RANGE,
                    DeliveryErrorCode.EXEED_MAX_DATE_RANGE.getMessage());
        }
    }
}
