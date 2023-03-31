package com.example.brg;

import com.example.brg.domain.delivery.model.Delivery;
import com.example.brg.domain.delivery.repository.DeliveryRepository;
import com.example.brg.domain.delivery.status.DeliveryStatus;
import com.example.brg.domain.user.model.User;
import com.example.brg.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public void run(String... args) {
        userRepository.save(new User("test1", "aaaaaaaaaaB.1", "Mr.Kim"));
        userRepository.save(new User("test2", "aaaaaaaaaaB.1", "Mr.Park"));
        userRepository.save(new User("test3", "aaaaaaaaaaB.1", "Mr.Lee"));

        User test1 = userRepository.findByUserId("test1").get();
        User test2 = userRepository.findByUserId("test2").get();
        User test3 = userRepository.findByUserId("test3").get();

        for(int i = 1; i <= 10; i++){
            deliveryRepository.save(new Delivery(test1, LocalDateTime.of(2023, 1, i, 0, 0),
                    LocalDateTime.of(2023, 1, i, 0, 0), "samsung-dong", DeliveryStatus.ARRIVED));
            deliveryRepository.save(new Delivery(test2, LocalDateTime.of(2023, 2, i, 0, 0),
                    LocalDateTime.of(2023, 2, i, 0, 0), "seocho-dong", DeliveryStatus.ARRIVED));
            deliveryRepository.save(new Delivery(test3, LocalDateTime.of(2023, 3, i, 0, 0),
                    LocalDateTime.of(2023, 3, i, 0, 0), "yeoksam-dong", DeliveryStatus.ARRIVED));
        }
    }
}
