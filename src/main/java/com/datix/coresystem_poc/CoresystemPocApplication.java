package com.datix.coresystem_poc;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.User;
import com.datix.coresystem_poc.entity.Wallbox;
import com.datix.coresystem_poc.entity.WallboxOwner;
import com.datix.coresystem_poc.repository.BookedTimeSlotRepository;
import com.datix.coresystem_poc.repository.BookingRepository;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.UserRepository;
import com.datix.coresystem_poc.repository.WallboxOwnerRepository;
import com.datix.coresystem_poc.repository.WallboxRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.datix.coresystem_poc.repository")
public class CoresystemPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoresystemPocApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = User.builder()
                        .name(name)
                        .email(name.toLowerCase() + "@domain.com").build();
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
        };
    }

    @Bean
    CommandLineRunner initialObjects(WallboxOwnerRepository ownerRepository,
                                     WallboxRepository wallboxRepository,
                                     RentedWallboxRepository rentedWallboxRepository,
                                     BookingRepository bookingRepository,
                                     BookedTimeSlotRepository bookedTimeSlotRepository,
                                     UserRepository userRepository
    ) {
        return args -> {
            WallboxOwner owner = WallboxOwner.builder().name("Heim").build();
            ownerRepository.save(owner);

            Wallbox wallbox = Wallbox.builder().physicalId("1").name("Wallbox 1").owner(owner).build();
            Wallbox wallbox2 = Wallbox.builder().physicalId("2").name("Wallbox 2").owner(owner).build();
            wallboxRepository.save(wallbox);
            wallboxRepository.save(wallbox2);

            RentedWallbox rentedWallbox = RentedWallbox.builder()
                    .wallbox(wallbox)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1)).build();
            RentedWallbox rentedWallbox2 = RentedWallbox.builder()
                    .wallbox(wallbox2)
                    .startTime(LocalDateTime.now().plusHours(12))
                    .endTime(LocalDateTime.now().plusDays(1).plusHours(12)).build();
            rentedWallboxRepository.save(rentedWallbox);
            rentedWallboxRepository.save(rentedWallbox2);
            rentedWallboxRepository.findAll().forEach(System.out::println);

            User user = User.builder()
                    .name("Greg")
                    .build();

            userRepository.save(user);

            BookedTimeSlot slot = BookedTimeSlot.builder()
                    .bookingTime(LocalDateTime.now())
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusMinutes(15))
                    .build();

            Booking booking = Booking.builder()
                    .wallbox(rentedWallbox)
                    .bookingOwner(user)
                    .bookedSlots(List.of(slot))
                    .pingInterval(6)
                    .build();

            bookedTimeSlotRepository.save(slot);
            bookingRepository.save(booking);
        };
    }
}
