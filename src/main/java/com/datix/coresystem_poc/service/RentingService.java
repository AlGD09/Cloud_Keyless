package com.datix.coresystem_poc.service;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.entity.Wallbox;
import com.datix.coresystem_poc.entity.WallboxOwner;
import com.datix.coresystem_poc.map.RentedWallboxMap;
import com.datix.coresystem_poc.map.WallboxMap;
import com.datix.coresystem_poc.map.WallboxOwnerMap;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.WallboxOwnerRepository;
import com.datix.coresystem_poc.repository.WallboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class RentingService {

    @Autowired
    RentedWallboxRepository rentedWallboxRepository;

    @Autowired
    WallboxOwnerRepository ownerRepository;

    @Autowired
    WallboxRepository wallboxRepository;

    public void registerRentedWallbox(RentedWallboxRegistrationDTO wallboxDTO) {
        WallboxOwner owner = ownerRepository.findByName(wallboxDTO.getOwnerName())
                .orElseGet(() -> ownerRepository.save(WallboxOwnerMap.toEntity(wallboxDTO)));
        Wallbox wallbox = wallboxRepository.findByName(wallboxDTO.getWallboxName())
                .orElseGet(() -> wallboxRepository.save(WallboxMap.toEntity(wallboxDTO, owner)));

        rentedWallboxRepository.save(RentedWallboxMap.toEntity(wallboxDTO, wallbox));
    }
}
