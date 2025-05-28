package com.datix.coresystem_poc.controller;

import com.datix.coresystem_poc.dto.BookingRegistrationDTO;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingService service;

    @GetMapping
    public List<Booking> getBookings() {
        return  service.getBookings();
    }

    @GetMapping("/upcoming")
    public ResponseEntity getUpcomingBooking(
            @RequestParam String username,
            @RequestParam Long wallboxId
    ) {
         return service.getUpcomingBooking(username, wallboxId);
    }

    @GetMapping("/invoices")
    public ResponseEntity getInvoices(
            @RequestParam String username
    ) {
        return service.getInvoices(username);
    }

    @GetMapping("/time-slot-length")
    public ResponseEntity<Integer> getTimeSlotLength() {
        return new ResponseEntity<>(service.getTimeSlotLength() ,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerBooking(@RequestBody BookingRegistrationDTO bookingDTO) {
        Booking booking = service.registerBooking(bookingDTO);
        System.out.println("Registering Booking:" + booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}