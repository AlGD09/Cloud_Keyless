package com.datix.coresystem_poc.map;

import com.datix.coresystem_poc.dto.InvoiceDTO;
import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.entity.ChargingTransaction;
import com.datix.coresystem_poc.entity.Wallbox;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.datix.coresystem_poc.util.BookingUtil.findEarliestStartTime;
import static com.datix.coresystem_poc.util.BookingUtil.findLatestEndTime;

public class BookingMap {

    private static float PRICE_PER_PING = 0.1F; //€
    private static float PRICE_PER_WH = 0.1F; //€

    public static InvoiceDTO toInvoice(Booking booking) {
        Wallbox wallbox = booking.getRentedWallbox().getWallbox();
        List<InvoiceDTO.InvoiceTimeSlotDTO> timeSlotDTOs = booking.getBookedSlots().stream()
                .map(timeSlot -> toDto(timeSlot))
                .toList();
        int pingsTotal = timeSlotDTOs.stream()
                .mapToInt(InvoiceDTO.InvoiceTimeSlotDTO::getPings)
                .sum();
        float pingPriceTotal = PRICE_PER_PING * pingsTotal;
        List<InvoiceDTO.InvoiceTransactionDTO> transactionDTOs = booking.getChargingTransactions().stream()
                .map(timeSlot -> toDto(timeSlot))
                .toList();
        int whTotal = transactionDTOs.stream()
                .mapToInt(InvoiceDTO.InvoiceTransactionDTO::getWh)
                .sum();
        float whPriceTotal = PRICE_PER_WH * whTotal;

        return InvoiceDTO.builder()
                .wallboxOwner(wallbox.getOwner().getName())
                .wallboxName(wallbox.getName())
                .startTime(findEarliestStartTime(booking.getBookedSlots()))
                .endTime(findLatestEndTime(booking.getBookedSlots()))

                .timeSlots(timeSlotDTOs)
                .pingsTotal(pingsTotal)
                .pricePerPing(PRICE_PER_PING)
                .pingPriceTotal(pingPriceTotal)

                .transactions(transactionDTOs)
                .whTotal(whTotal)
                .pricePerWh(PRICE_PER_WH)
                .whPriceTotal(whPriceTotal)

                .priceTotal(pingPriceTotal + whPriceTotal)
                .build();
    }

    private static InvoiceDTO.InvoiceTimeSlotDTO toDto(BookedTimeSlot timeSlot) {
                    LocalDateTime bookingTime = timeSlot.getBookingTime();
                    LocalDateTime startTime = timeSlot.getStartTime();
                    int pings = 0;
                    if (bookingTime != null && startTime != null) {
                        pings = (int) Duration.between(bookingTime, startTime).toMinutes();
                    }
                    return new InvoiceDTO.InvoiceTimeSlotDTO(timeSlot, pings);
    }

    private static InvoiceDTO.InvoiceTransactionDTO toDto(ChargingTransaction transaction) {
        return new InvoiceDTO.InvoiceTransactionDTO(transaction,
                transaction.getEndWattsPerHour() - transaction.getStartWattsPerHour());
    }
}
