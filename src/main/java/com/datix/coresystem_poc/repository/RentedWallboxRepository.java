package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.RentedWallbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedWallboxRepository extends CrudRepository<RentedWallbox, Long> {}