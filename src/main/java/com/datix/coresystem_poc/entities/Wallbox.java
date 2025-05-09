package com.datix.coresystem_poc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Wallbox {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String physicalId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private WallboxOwner owner;

}
