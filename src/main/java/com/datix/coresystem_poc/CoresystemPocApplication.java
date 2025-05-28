package com.datix.coresystem_poc;

import com.datix.coresystem_poc.config.PingConfig;
import com.datix.coresystem_poc.config.TimeSlotConfig;
import com.datix.coresystem_poc.dto.TransactionDetailsDTO;
import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.entity.ChargingTransaction;
import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.User;
import com.datix.coresystem_poc.entity.Wallbox;
import com.datix.coresystem_poc.entity.WallboxOwner;
import com.datix.coresystem_poc.map.TransactionMap;
import com.datix.coresystem_poc.repository.BookedTimeSlotRepository;
import com.datix.coresystem_poc.repository.BookingRepository;
import com.datix.coresystem_poc.repository.ChargingTransactionRepository;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.UserRepository;
import com.datix.coresystem_poc.repository.WallboxOwnerRepository;
import com.datix.coresystem_poc.repository.WallboxRepository;
import com.datix.coresystem_poc.service.SteveService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.datix.coresystem_poc.repository")
public class CoresystemPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoresystemPocApplication.class, args);
    }

    @Bean
    CommandLineRunner initialObjects(WallboxOwnerRepository ownerRepository,
                                     WallboxRepository wallboxRepository,
                                     RentedWallboxRepository rentedWallboxRepository,
                                     BookingRepository bookingRepository,
                                     BookedTimeSlotRepository bookedTimeSlotRepository,
                                     UserRepository userRepository,
                                     TimeSlotConfig timeSlotConfig,
                                     PingConfig pingConfig,
                                     SteveService steveService,
                                     ChargingTransactionRepository transactionRepository
    ) {
        return args -> {
            WallboxOwner owner = WallboxOwner.builder().name("Heim").build();
            ownerRepository.save(owner);

            Wallbox wallbox = Wallbox.builder().physicalId("1").name("Wallbox 1").owner(owner).build();
            Wallbox wallbox2 = Wallbox.builder().physicalId("2").name("Wallbox 2").owner(owner).build();
            wallboxRepository.save(wallbox);
            wallboxRepository.save(wallbox2);

            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

            RentedWallbox rentedWallbox = RentedWallbox.builder()
                    .wallbox(wallbox)
                    .startTime(now.plusMinutes(timeSlotConfig.getLength()))
                    .endTime(now.plusDays(1)).build();
            RentedWallbox rentedWallbox2 = RentedWallbox.builder()
                    .wallbox(wallbox2)
                    .startTime(now.plusHours(12).plusMinutes(timeSlotConfig.getLength() * 3))
                    .endTime(now.plusDays(1).plusHours(12)).build();
            rentedWallboxRepository.save(rentedWallbox);
            rentedWallboxRepository.save(rentedWallbox2);
            rentedWallboxRepository.findAll().forEach(System.out::println);

            User user = User.builder()
                    .name("Elekey")
                    .build();

            userRepository.save(user);

            BookedTimeSlot slot = BookedTimeSlot.builder()
                    .bookingTime(LocalDateTime.now())
                    .startTime(LocalDateTime.now().plusMinutes(5))
                    .endTime(LocalDateTime.now().plusMinutes(15))
                    .build();

            Booking booking = Booking.builder()
                    .rentedWallbox(rentedWallbox)
                    .bookingUser(user)
                    .bookedSlots(List.of(slot))
                    .build();

            System.out.println(booking);

            bookedTimeSlotRepository.save(slot);
            bookingRepository.save(booking);

//            TransactionDetailsDTO response = steveService.getTransactionDetails(1);
//            ChargingTransaction transaction = TransactionMap.toEntity(response);
//
//            System.out.println(transaction);
//            transactionRepository.save(transaction);

//            steveService.triggerRemoteStop("whatever", 1L);
//            steveService.triggerRemoteStop("whatever", 1L);
        };
    }
}
