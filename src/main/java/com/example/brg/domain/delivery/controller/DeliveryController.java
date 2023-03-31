package com.example.brg.domain.delivery.controller;

import com.example.brg.domain.delivery.request.DeliveryRequest;
import com.example.brg.domain.delivery.response.DeliveryResponse;
import com.example.brg.domain.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getDeliveries(
            @RequestParam String userId,
            @RequestBody DeliveryRequest deliveryRequest
    ){
        List<DeliveryResponse> deliveries =
                deliveryService.getDeliveries(
                        userId,
                        deliveryRequest.getStartDate().atStartOfDay(),
                        deliveryRequest.getEndDate().atStartOfDay()
                );

        return ResponseEntity.ok().body(deliveries);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(@PathVariable Long id,
                              @RequestParam String userId,
                              @RequestBody String destination
    ){
        deliveryService.updateAddress(id, userId, destination);
    }
}
