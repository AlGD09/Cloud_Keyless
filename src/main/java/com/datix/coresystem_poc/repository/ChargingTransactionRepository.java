package com.datix.coresystem_poc.repository;

import com.datix.coresystem_poc.entity.ChargingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingTransactionRepository extends JpaRepository<ChargingTransaction, Long> {}
