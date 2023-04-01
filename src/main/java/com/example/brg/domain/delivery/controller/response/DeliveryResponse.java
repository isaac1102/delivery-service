package com.example.brg.domain.delivery.controller.response;

import com.example.brg.domain.delivery.status.DeliveryStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryResponse {
    private String userId;
    private LocalDateTime orderDate;
    private String destination;
    private LocalDateTime arrivalDate;
    private DeliveryStatus status;
}
