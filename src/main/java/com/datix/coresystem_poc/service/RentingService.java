package com.datix.coresystem_poc.service;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.entity.WallboxOwner;
import com.datix.coresystem_poc.map.WallboxOwnerMap;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.WallboxOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class RentingService {

    @Autowired
    RentedWallboxRepository rentedWallboxRepository;

    @Autowired
    WallboxOwnerRepository ownerRepository;

    public void registerRentedWallbox(RentedWallboxRegistrationDTO wallbox) {
        AtomicReference<WallboxOwner> owner = null;
        ownerRepository.findByName(wallbox.getOwnerName())
                .ifPresentOrElse(
                        foundOwner -> owner.set(foundOwner),
                        () -> owner.set(
                                ownerRepository.save(WallboxOwnerMap.toEntity(wallbox))));

//        rentedWallboxRepository.save(wallbox);
    }
}
