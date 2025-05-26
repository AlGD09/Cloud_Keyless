package com.datix.coresystem_poc.util;

import com.datix.coresystem_poc.entity.BookedTimeSlot;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class BookingUtil {

    public static LocalDateTime findEarliestStartTime(List<BookedTimeSlot> slots) {
        if (slots == null || slots.isEmpty()) {
            return null;
        }

        return slots.stream()
                .map(BookedTimeSlot::getStartTime)
                .filter(time -> time != null)
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }

    public static LocalDateTime findLatestEndTime(List<BookedTimeSlot> slots) {
        if (slots == null || slots.isEmpty()) {
            return null;
        }

        return slots.stream()
                .map(BookedTimeSlot::getEndTime)
                .filter(time -> time != null)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

}

