package com.datix.coresystem_poc.dto;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import lombok.Value;

import java.time.LocalDateTime;

import static com.datix.coresystem_poc.util.BookingUtil.findEarliestStartTime;
import static com.datix.coresystem_poc.util.BookingUtil.findLatestEndTime;

@Value
public class UpcomingBooking {

    private Booking booking;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public UpcomingBooking(Booking booking) {
        this.booking = booking;
        this.startTime = findEarliestStartTime(booking.getBookedSlots());
        this.endTime = findLatestEndTime(booking.getBookedSlots());
    }
}