package com.datix.coresystem_poc.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Required by JPA
@AllArgsConstructor
@Builder
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private RentedWallbox wallbox;

    @ManyToOne(fetch = FetchType.EAGER)
    private User bookingOwner;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BookedTimeSlot> bookedSlots;

    @Builder.Default
    private Integer pingInterval = 6;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private List<ChargingTransaction> chargingTransactions;

}