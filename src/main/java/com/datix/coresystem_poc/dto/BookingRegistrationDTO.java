package com.datix.coresystem_poc.dto;

import com.datix.coresystem_poc.entity.BookedTimeSlot;

import lombok.Value;

import java.util.List;

@Value
public class BookingRegistrationDTO {

    private Long rentedWallboxId;
    private Long bookingUserId;
    private List<BookedTimeSlot> bookedSlots;

}
