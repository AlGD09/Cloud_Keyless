package com.datix.coresystem_poc.controllers;

import com.datix.coresystem_poc.dto.RentedWallboxRegistrationDTO;
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

    @PostMapping
    public ResponseEntity<Void> registerRentedWallbox(@RequestBody RentedWallboxRegistrationDTO wallbox) {
        System.out.println(wallbox);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
