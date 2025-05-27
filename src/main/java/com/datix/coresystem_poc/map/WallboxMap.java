package com.datix.coresystem_poc.map;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.entity.Wallbox;
import com.datix.coresystem_poc.entity.WallboxOwner;

public class WallboxMap {

    public static Wallbox toEntity(RentedWallboxRegistrationDTO wallboxDTO, WallboxOwner owner) {
        return Wallbox.builder()
                .physicalId(wallboxDTO.getWallboxId())
                .name(wallboxDTO.getWallboxName())
                .owner(owner)
                .build();
    }

    public static Wallbox toEntity(RentedWallboxRegistrationDTO wallboxDTO) {
        return Wallbox.builder()
                .physicalId(wallboxDTO.getWallboxId())
                .name(wallboxDTO.getWallboxName())
                .owner(WallboxOwnerMap.toEntity(wallboxDTO))
                .build();
    }
}
