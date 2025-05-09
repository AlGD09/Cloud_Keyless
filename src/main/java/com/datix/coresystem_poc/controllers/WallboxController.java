package com.datix.coresystem_poc.controllers;

import com.datix.coresystem_poc.entities.RentedWallbox;
import com.datix.coresystem_poc.repositories.RentedWallboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/wallboxes")
public class WallboxController {

    @Autowired
    private RentedWallboxRepository rentedWallboxRepositoryRepository;

    @GetMapping("/rented")
    public List<RentedWallbox> getUsers() {
        return (List<RentedWallbox>) rentedWallboxRepositoryRepository.findAll();
    }

}
