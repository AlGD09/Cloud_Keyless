package com.keyless.rexroth.controller;

import com.keyless.rexroth.dto.SmartphoneRegistrationDTO;
import com.keyless.rexroth.service.SmartphoneService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/devices")


public class SmartphoneController {


    @Autowired
    SmartphoneService smartphoneService;


    @PostMapping("/request")
    public ResponseEntity<?> requestToken(@RequestBody SmartphoneRegistrationDTO dto) {
        // smartphoneService prüft device + hash und erzeugt Token (oder null bei Fehler)
        String token = smartphoneService.authenticateAndGenerateToken(dto.getDeviceId(), dto.getSecretHash());

        if (token == null) {
            return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));
        }

        return ResponseEntity.ok(Map.of(
                "auth_token", token
        ));
    }





}
