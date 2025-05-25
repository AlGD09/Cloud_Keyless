package com.datix.coresystem_poc.controller;

import com.datix.coresystem_poc.dto.BookingRegistrationDTO;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {

    @Autowired
    BookingService service;

    @PostMapping
    public ResponseEntity<Void> registerBooking(@RequestBody BookingRegistrationDTO bookingDTO) {
        Booking booking = service.registerBooking(bookingDTO);
        System.out.println(booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}