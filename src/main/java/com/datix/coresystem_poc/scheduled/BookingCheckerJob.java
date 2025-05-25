package com.datix.coresystem_poc.scheduled;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingCheckerJob {

    @Autowired
    BookingRepository bookingRepository;

    @Scheduled(cron = "0 * * * * ?") // every minute at 0 seconds
    public void checkCurrentBookings() {
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings = bookingRepository.findAll();

        System.out.println("Scheduled task running every minute - " + now);


//        for (Booking booking : bookings) {
//            for (BookedTimeSlot slot : booking.getBookedSlots()) {
//                if (slot.getStartTime() != null && slot.getEndTime() != null) {
//                    boolean isActive = !now.isBefore(slot.getStartTime()) && now.isBefore(slot.getEndTime());
//                    if (isActive) {
//                        System.out.println("Active booking found: Booking ID " + booking.getId()
//                                + " Slot ID " + slot.getId() + " Current time " + now);
//                        // Add your logic here for active slots
//                    }
//                }
//            }
//        }
    }
}

