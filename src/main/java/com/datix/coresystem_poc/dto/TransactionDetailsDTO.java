package com.datix.coresystem_poc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailsDTO {

    private TransactionDTO transaction;
    private List<MeterValueDTO> values;
    private ZonedDateTime nextTransactionStart;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDTO {
        private int id;
        private int connectorId;
        private int chargeBoxPk;
        private int ocppTagPk;
        private String chargeBoxId;
        private String ocppIdTag;
        private int startValue;
        private ZonedDateTime startTimestamp;
        private int stopValue;
        private String stopReason;
        private ZonedDateTime stopTimestamp;
        private String stopEventActor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeterValueDTO {
        private ZonedDateTime valueTimestamp;
        private String value;
        private String readingContext;
        private String format;
        private String measurand;
        private String location;
        private String unit;
        private String phase;  // Nullable
    }
}
