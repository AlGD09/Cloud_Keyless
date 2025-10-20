package com.keyless.rexroth.entity;


import jakarta.persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "smartphones")
public class Smartphone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String deviceId;

    private String userName;
    private String secretHash;
    private String bleId;
    private String status;
    private LocalDateTime lastSeen;

    public Smartphone() {}

    public Smartphone(String deviceId, String userName, String secretHash, String bleId, String status) {
        this.deviceId = deviceId;
        this.userName = userName;
        this.secretHash = secretHash;
        this.bleId = bleId;
        this.status = status;
        this.lastSeen = LocalDateTime.now();
    }





}
