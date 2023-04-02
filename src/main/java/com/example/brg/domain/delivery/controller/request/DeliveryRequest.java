package com.example.brg.domain.delivery.controller.request;

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
    }
}
