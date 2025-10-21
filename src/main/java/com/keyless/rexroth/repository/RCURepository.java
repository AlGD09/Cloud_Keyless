package com.keyless.rexroth.repository;

import com.keyless.rexroth.entity.RCU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RCURepository extends JpaRepository<RCU, Long>{
    RCU findByRcuId(String rcuId);
}
