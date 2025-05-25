package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedTimeSlotRepository extends JpaRepository<BookedTimeSlot, Long> {}