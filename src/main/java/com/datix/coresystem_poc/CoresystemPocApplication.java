package com.datix.coresystem_poc;

import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.User;
import com.datix.coresystem_poc.entity.Wallbox;
import com.datix.coresystem_poc.entity.WallboxOwner;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.UserRepository;
import com.datix.coresystem_poc.repository.WallboxOwnerRepository;
import com.datix.coresystem_poc.repository.WallboxRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
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
    CommandLineRunner initialObjects(WallboxOwnerRepository ownerRepository, WallboxRepository wallboxRepository,
                                     RentedWallboxRepository rentedWallboxRepository) {
        return args -> {
            WallboxOwner owner = WallboxOwner.builder().name("Alice").build();
            ownerRepository.save(owner);

            Wallbox wallbox = Wallbox.builder().physicalId("1").name("Wallbox 1").owner(owner).build();
            wallboxRepository.save(wallbox);

            RentedWallbox rentedWallbox = RentedWallbox.builder()
                    .wallbox(wallbox)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1)).build();
            rentedWallboxRepository.save(rentedWallbox);
            rentedWallboxRepository.findAll().forEach(System.out::println);
        };
    }
}
