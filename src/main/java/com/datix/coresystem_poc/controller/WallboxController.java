package com.datix.coresystem_poc.controller;

import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.Wallbox;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.WallboxRepository;
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

    @Autowired
    private WallboxRepository wallboxRepositoryRepository;

    @GetMapping()
    public List<Wallbox> getAll() {
        return (List<Wallbox>) wallboxRepositoryRepository.findAll();
    }

    @GetMapping("/rented")
    public List<RentedWallbox> getAllRented() {
        return (List<RentedWallbox>) rentedWallboxRepositoryRepository.findAll();
    }

}
