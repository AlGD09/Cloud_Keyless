package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}