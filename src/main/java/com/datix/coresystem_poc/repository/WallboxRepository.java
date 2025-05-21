package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.Wallbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WallboxRepository extends CrudRepository<Wallbox, Long> {
    Optional<Wallbox> findByName(String name);
}


