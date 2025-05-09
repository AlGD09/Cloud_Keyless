package com.datix.coresystem_poc.repositories;

import com.datix.coresystem_poc.entities.Wallbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallboxRepository extends CrudRepository<Wallbox, Long> {}


