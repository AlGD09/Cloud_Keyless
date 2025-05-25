package com.datix.coresystem_poc.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class BookingRegistrationDTO {

    private Long rentedWallboxId;
    private Long bookingUserId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
