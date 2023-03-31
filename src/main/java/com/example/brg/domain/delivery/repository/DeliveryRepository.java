package com.example.brg.domain.delivery.repository;

import com.example.brg.domain.delivery.model.Delivery;
import com.example.brg.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByUserAndOrderDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
    Optional<Delivery> findByIdAndUser(Long id, User user);
}
