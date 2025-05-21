package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.WallboxOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallboxOwnerRepository extends CrudRepository<WallboxOwner, Long> {}



