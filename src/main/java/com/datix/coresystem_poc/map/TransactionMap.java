package com.datix.coresystem_poc.map;

import com.datix.coresystem_poc.dto.TransactionDetailsDTO;
import com.datix.coresystem_poc.entity.ChargingTransaction;

import java.time.ZoneId;

public class TransactionMap {

    public static ChargingTransaction toEntity(TransactionDetailsDTO transactionDetails) {
        return ChargingTransaction.builder()
                .transactionId(transactionDetails.getTransaction().getId())
                .startWattsPerHour(transactionDetails.getTransaction().getStartValue())
                .endWattsPerHour(transactionDetails.getTransaction().getStopValue())
                .startTime(transactionDetails.getTransaction().getStartTimestamp()
                        .withZoneSameInstant(ZoneId.of("Europe/Berlin")).toLocalDateTime())
                .endTime(transactionDetails.getTransaction().getStopTimestamp()
                        .withZoneSameInstant(ZoneId.of("Europe/Berlin")).toLocalDateTime())
                .build();
    }

}
