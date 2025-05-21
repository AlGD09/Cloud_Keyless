package com.datix.coresystem_poc.map;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.Wallbox;

public class RentedWallboxMap {

    public static RentedWallbox toEntity(RentedWallboxRegistrationDTO wallboxDTO, Wallbox wallbox) {
        return RentedWallbox.builder()
                .wallbox(wallbox)
                .startTime(wallboxDTO.getStartTime())
                .endTime(wallboxDTO.getEndTime())
                .build();
    }

    public static RentedWallbox toEntity(RentedWallboxRegistrationDTO wallboxDTO) {
        return RentedWallbox.builder()
                .wallbox(WallboxMap.toEntity(wallboxDTO))
                .startTime(wallboxDTO.getStartTime())
                .endTime(wallboxDTO.getEndTime())
                .build();
    }

}
