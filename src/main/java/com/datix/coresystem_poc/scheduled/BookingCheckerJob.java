package com.datix.coresystem_poc.scheduled;

import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.repository.BookingRepository;
import com.datix.coresystem_poc.service.SteveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.datix.coresystem_poc.util.BookingUtil.findEarliestStartTime;
import static com.datix.coresystem_poc.util.BookingUtil.findLatestEndTime;

@Component
public class BookingCheckerJob {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SteveService steveService;

    private static Set<Booking> activeBookings = new HashSet<>();

    @Scheduled(cron = "0 * * * * ?") // every minute at 0 seconds
    public void checkCurrentBookings() {
        LocalDateTime utcNow = LocalDateTime.now();
        List<Booking> bookings = bookingRepository.findAll();

        System.out.println("Scheduled booking activating task running every minute - " + utcNow);
        checkBookingsToActivate(utcNow, bookings);
        checkBookingsToDeactivate(utcNow);
        ;
    }

    private void checkBookingsToActivate(LocalDateTime utcNow, List<Booking> bookingsToCheck) {
        for (Booking booking : bookingsToCheck) {
            LocalDateTime bookingStartTime = findEarliestStartTime(booking.getBookedSlots());
            LocalDateTime bookingEndTime = findLatestEndTime(booking.getBookedSlots());
            if (bookingStartTime != null && bookingEndTime != null) {
                boolean isActive = !utcNow.isBefore(bookingStartTime) && utcNow.isBefore(bookingEndTime);
                if (isActive && activeBookings.add(booking)) {
                    steveService.turnOff(booking.getRentedWallbox().getWallbox().getPhysicalId());
                    System.out.println(" Current time " + utcNow + "; Activating booking with ID " + booking.getId()
                            + "; Active until " + bookingEndTime);
                }
            }
        }
    }

    private void checkBookingsToDeactivate(LocalDateTime utcNow) {
        if (!activeBookings.isEmpty()) {
            Set<Booking> toRemove = new HashSet<>();

            for (Booking activeBooking : activeBookings) {
                LocalDateTime bookingEndTime = findLatestEndTime(activeBooking.getBookedSlots());
                if (bookingEndTime != null && utcNow.isAfter(bookingEndTime)) {
                    toRemove.add(activeBooking);
                    steveService.turnOn(activeBooking.getRentedWallbox().getWallbox().getPhysicalId());
                    System.out.println(" Current UTC time " + utcNow + "; Deactivating booking with ID " + activeBooking.getId());
                }
            }

            activeBookings.removeAll(toRemove);
        }
    }
}

