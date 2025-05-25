package com.datix.coresystem_poc.service;

import com.datix.coresystem_poc.config.TimeSlotConfig;
import com.datix.coresystem_poc.dto.BookingRegistrationDTO;
import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.User;
import com.datix.coresystem_poc.repository.BookedTimeSlotRepository;
import com.datix.coresystem_poc.repository.BookingRepository;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookedTimeSlotRepository bookedTimeSlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentedWallboxRepository rentedWallboxRepository;

    @Autowired
    TimeSlotConfig timeSlotConfig;

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Integer getTimeSlotLength() {
        return timeSlotConfig.getLength();
    }

    public Booking registerBooking(BookingRegistrationDTO bookingDTO) {
        User user = userRepository.findById(bookingDTO.getBookingUserId())
                .orElseThrow();
        RentedWallbox rentedWallbox = rentedWallboxRepository.findById(bookingDTO.getRentedWallboxId())
                .orElseThrow();
        List<BookedTimeSlot> timeSlots = bookedTimeSlotRepository.saveAll(
                createTimeSlots(bookingDTO.getStartTime(), bookingDTO.getEndTime()));

        return bookingRepository.save(Booking.builder()
                .bookingUser(user)
                .rentedWallbox(rentedWallbox)
                .bookedSlots(timeSlots)
                .build()
        );
    }

    private List<BookedTimeSlot> createTimeSlots(LocalDateTime startTime, LocalDateTime endTime) {
        List<BookedTimeSlot> timeSlots = new ArrayList<>();
        LocalDateTime benchmarkTime = startTime;
        final int timeSlotLength = timeSlotConfig.getLength();

        while (benchmarkTime.isBefore(endTime)) {
            BookedTimeSlot slot = BookedTimeSlot.builder()
                    .bookingTime(LocalDateTime.now())
                    .startTime(benchmarkTime)
                    .endTime(benchmarkTime.plusMinutes(timeSlotLength)
                            .minusSeconds(1)) // Prevent overlap
                    .build();
            timeSlots.add(slot);
            benchmarkTime = benchmarkTime.plusMinutes(timeSlotLength);
        }

        return timeSlots;
    }

}
