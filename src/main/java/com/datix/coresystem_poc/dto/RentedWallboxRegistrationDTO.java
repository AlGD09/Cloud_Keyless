package com.datix.coresystem_poc.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RentedWallboxRegistrationDTO {
    String wallboxId;
    String wallboxName;
    String ownerName;
    LocalDateTime startTime;
    LocalDateTime endTime;

}
