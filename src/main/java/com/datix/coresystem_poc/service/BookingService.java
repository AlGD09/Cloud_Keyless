package com.datix.coresystem_poc.service;

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

    public Booking registerBooking(BookingRegistrationDTO bookingDTO) {
        User user = userRepository.findById(bookingDTO.getBookingUserId())
                .orElseThrow();
        RentedWallbox rentedWallbox = rentedWallboxRepository.findById(bookingDTO.getRentedWallboxId())
                .orElseThrow();
        List<BookedTimeSlot> timeSlots = bookedTimeSlotRepository.saveAll(bookingDTO.getBookedSlots());

        return bookingRepository.save(Booking.builder()
                .bookingUser(user)
                .rentedWallbox(rentedWallbox)
                .bookedSlots(timeSlots)
                .build()
        );
    }

}
