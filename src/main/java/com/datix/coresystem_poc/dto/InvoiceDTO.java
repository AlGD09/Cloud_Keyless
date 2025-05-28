package com.datix.coresystem_poc.dto;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.ChargingTransaction;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class InvoiceDTO {

    private String wallboxOwner;
    private String wallboxName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private List<InvoiceTimeSlotDTO> timeSlots;
    private int pingsTotal;
    private float pricePerPing;
    private float pingPriceTotal;

    private List<InvoiceTransactionDTO> transactions;
    private int whTotal;
    private float pricePerWh;
    private float whPriceTotal;

    private float priceTotal;

    @Value
    public static class InvoiceTimeSlotDTO {
        BookedTimeSlot timeSlot;
        int pings;
    }

    @Value
    public static class InvoiceTransactionDTO {
        ChargingTransaction transaction;
        int wh;
    }
}
