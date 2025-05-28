package com.datix.coresystem_poc.controller;

import com.datix.coresystem_poc.service.SteveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/steve")
public class SteveController {

    @Autowired
    SteveService service;

    private final String WALLBOX_PHYSICAL_ID = "ABL_10355221";

    @PostMapping("/remoteStart")
    public ResponseEntity<Void> triggerRemoteStart() {
       service.triggerRemoteStart(WALLBOX_PHYSICAL_ID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remoteStop")
    public ResponseEntity<Void> triggerRemoteStop(@RequestParam("bookingId") Long bookingId) {
        service.triggerRemoteStop(WALLBOX_PHYSICAL_ID, bookingId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/turnOn")
    public ResponseEntity<Void> turnOn() {
        service.turnOn(WALLBOX_PHYSICAL_ID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/turnOff")
    public ResponseEntity<Void> turnOff() {
        service.turnOff(WALLBOX_PHYSICAL_ID);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
