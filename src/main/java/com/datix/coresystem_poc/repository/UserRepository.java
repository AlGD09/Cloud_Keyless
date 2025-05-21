package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}

