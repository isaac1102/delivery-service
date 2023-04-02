package com.example.brg.domain.delivery.controller;

import com.example.brg.domain.delivery.controller.request.DeliveryRequest;
import com.example.brg.domain.delivery.controller.response.DeliveryResponse;
import com.example.brg.domain.delivery.exception.DeliveryErrorCode;
import com.example.brg.domain.delivery.exception.DeliveryException;
import com.example.brg.domain.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getDeliveries(
            Authentication authentication,
            @RequestBody DeliveryRequest deliveryRequest
    ){
        if (deliveryRequest.getEndDate().isBefore(deliveryRequest.getStartDate())) {
            throw new DeliveryException(DeliveryErrorCode.INVALID_DATE_RANGE,
                    DeliveryErrorCode.INVALID_DATE_RANGE.getMessage());
        }

        if (deliveryRequest.getStartDate().plusDays(3).isBefore(deliveryRequest.getEndDate())) {
            throw new DeliveryException(DeliveryErrorCode.EXEED_MAX_DATE_RANGE,
                    DeliveryErrorCode.EXEED_MAX_DATE_RANGE.getMessage());
        }

        List<DeliveryResponse> deliveries =
                deliveryService.getDeliveries(
                        authentication.getName(),
                        deliveryRequest.getStartDate().atStartOfDay(),
                        deliveryRequest.getEndDate().atStartOfDay()
                );

        return ResponseEntity.ok().body(deliveries);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(Authentication authentication,
                              @PathVariable Long id,
                              @RequestBody String destination
    ){
        deliveryService.updateAddress(id, authentication.getName(), destination);
    }
}
