package com.example.brg.domain.delivery.model;

import com.example.brg.domain.user.model.User;
import com.example.brg.domain.delivery.status.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_test")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "destination")
    private String destination;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(User user, LocalDateTime orderDate, LocalDateTime arrivalDate, String destination, DeliveryStatus status){
        this.user = user;
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
        this.destination = destination;
        this.status = status;
    }

    public void updateDestination(String destination) {
        this.destination = destination;
    }
}
