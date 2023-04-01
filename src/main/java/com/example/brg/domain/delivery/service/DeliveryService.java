package com.example.brg.domain.delivery.service;

import com.example.brg.domain.delivery.exception.DeliveryErrorCode;
import com.example.brg.domain.delivery.exception.DeliveryException;
import com.example.brg.domain.delivery.model.Delivery;
import com.example.brg.domain.delivery.status.DeliveryStatus;
import com.example.brg.domain.user.model.User;
import com.example.brg.domain.delivery.controller.response.DeliveryResponse;
import com.example.brg.domain.user.exception.UserException;
import com.example.brg.domain.user.exception.UserErrorCode;
import com.example.brg.domain.delivery.repository.DeliveryRepository;
import com.example.brg.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;

    public List<DeliveryResponse> getDeliveries(
            String userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new UserException(
                                        UserErrorCode.USER_NOT_FOUND,
                                        UserErrorCode.USER_NOT_FOUND.getMessage()));

        List<Delivery> deliveries =
                deliveryRepository.findAllByUserAndOrderDateBetween(user, startDate, endDate);

        return deliveries.stream().map(delivery -> DeliveryResponse.builder()
                .userId(delivery.getUser().getUserId())
                .orderDate(delivery.getOrderDate())
                .destination(delivery.getDestination())
                .arrivalDate(delivery.getArrivalDate())
                .status(delivery.getStatus())
                .build()).toList();
    }


    public void updateAddress(Long id, String userId, String destination) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new UserException(UserErrorCode.USER_NOT_FOUND,
                        UserErrorCode.USER_NOT_FOUND.getMessage())
        );

        Delivery delivery = deliveryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.NOT_FOUND_DELIVERY,
                        DeliveryErrorCode.NOT_FOUND_DELIVERY.getMessage()));

        if (delivery.getStatus() != DeliveryStatus.WAITING) {
            throw new DeliveryException(DeliveryErrorCode.NOT_UPDATABLE_STATUS,
                    DeliveryErrorCode.NOT_UPDATABLE_STATUS.getMessage());
        }

        delivery.updateDestination(destination);
        deliveryRepository.save(delivery);
    }
}
