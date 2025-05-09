package com.datix.coresystem_poc.repositories;

import com.datix.coresystem_poc.entities.WallboxOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallboxOwnerRepository extends CrudRepository<WallboxOwner, Long> {}



