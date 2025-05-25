package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import org.springframework.data.repository.CrudRepository;

public interface BookedTimeSlotRepository extends CrudRepository<BookedTimeSlot, Long> {}