package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingUserNameAndRentedWallbox_Wallbox_Id(String name, Long wallboxId);
    List<Booking> findByBookingUserName(String name);
}