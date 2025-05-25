package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {}