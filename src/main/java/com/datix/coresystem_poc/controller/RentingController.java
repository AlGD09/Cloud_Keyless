package com.datix.coresystem_poc.controller;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
import com.datix.coresystem_poc.service.RentingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/wallbox-renting", consumes = MediaType.APPLICATION_JSON_VALUE)
public class RentingController {

    @Autowired
    RentingService service;

    @PostMapping
    public ResponseEntity<Void> registerRentedWallbox(@RequestBody RentedWallboxRegistrationDTO wallbox) {
        service.registerRentedWallbox(wallbox);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
