package com.datix.coresystem_poc.map;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.entity.WallboxOwner;

public class WallboxOwnerMap {

    public static WallboxOwner toEntity(RentedWallboxRegistrationDTO wallboxDTO) {
        return WallboxOwner.builder().name(wallboxDTO.getOwnerName()).build();
    }

}
