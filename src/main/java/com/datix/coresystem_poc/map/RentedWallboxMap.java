package com.datix.coresystem_poc.map;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.entity.RentedWallbox;

public class RentedWallboxMap {

    static RentedWallbox toEntity(RentedWallboxRegistrationDTO wallboxDTO) {
        return RentedWallbox.builder().build();
    }

}
