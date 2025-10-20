package com.datix.coresystem_poc.service;

import com.datix.coresystem_poc.config.TimeSlotConfig;
import com.datix.coresystem_poc.dto.BookingRegistrationDTO;
import com.datix.coresystem_poc.dto.InvoiceDTO;
import com.datix.coresystem_poc.dto.UpcomingBooking;
import com.datix.coresystem_poc.entity.BookedTimeSlot;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.entity.RentedWallbox;
import com.datix.coresystem_poc.entity.User;
import com.datix.coresystem_poc.repository.BookedTimeSlotRepository;
import com.datix.coresystem_poc.repository.BookingRepository;
import com.datix.coresystem_poc.repository.RentedWallboxRepository;
import com.datix.coresystem_poc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.datix.coresystem_poc.map.BookingMap.toInvoice;
import static com.datix.coresystem_poc.util.BookingUtil.findEarliestStartTime;
import static com.datix.coresystem_poc.util.BookingUtil.findLatestEndTime;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookedTimeSlotRepository bookedTimeSlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentedWallboxRepository rentedWallboxRepository;

    @Autowired
    TimeSlotConfig timeSlotConfig;

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public ResponseEntity<UpcomingBooking> getUpcomingBooking(String username, Long wallboxId) {
        List<Booking> bookings = bookingRepository.findByBookingUserNameAndRentedWallbox_Wallbox_Id(username, wallboxId);
        LocalDateTime now = LocalDateTime.now();

        Optional<Booking> optionalBooking = bookings.stream()
                // Only consider bookings where latest endTime is still in the future
                .filter(b -> {
                    LocalDateTime latestEndTime = findLatestEndTime(b.getBookedSlots());
                    return latestEndTime != null && latestEndTime.isAfter(now);
                })
                // Get booking with earliest startTime
                .min(Comparator.comparing(b -> {
                    LocalDateTime earliestStartTime = findEarliestStartTime(b.getBookedSlots());
                    return earliestStartTime != null ? earliestStartTime : LocalDateTime.MAX;
                }));

        return optionalBooking
                .map(booking -> ResponseEntity.ok(new UpcomingBooking(booking)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<List<InvoiceDTO>> getInvoices(String username) {
        List<Booking> bookings = bookingRepository.findByBookingUserName(username);
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookingsFiltered = bookings.stream()
                // Only consider bookings where latest endTime has passed

                .filter(b -> {
                    LocalDateTime latestEndTime = findLatestEndTime(b.getBookedSlots());
                    return latestEndTime != null && latestEndTime.isBefore(now);
                }).collect(Collectors.toList());

        List<InvoiceDTO> invoices = bookingsFiltered.stream().map(b -> toInvoice(b)).toList();

        return invoices.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(invoices);
    }

    public Integer getTimeSlotLength() {
        return timeSlotConfig.getLength();
    }

    public Booking registerBooking(BookingRegistrationDTO bookingDTO) {
        User user = userRepository.findById(bookingDTO.getBookingUserId())
                .orElseThrow();
        RentedWallbox rentedWallbox = rentedWallboxRepository.findById(bookingDTO.getRentedWallboxId())
                .orElseThrow();
        List<BookedTimeSlot> timeSlots = bookedTimeSlotRepository.saveAll(
                createTimeSlots(bookingDTO.getStartTime(), bookingDTO.getEndTime()));

        return bookingRepository.save(Booking.builder()
                .bookingUser(user)
                .rentedWallbox(rentedWallbox)
                .bookedSlots(timeSlots)
                .build()
        );
    }

    private List<BookedTimeSlot> createTimeSlots(LocalDateTime startTime, LocalDateTime endTime) {
        List<BookedTimeSlot> timeSlots = new ArrayList<>();
        LocalDateTime benchmarkTime = startTime;
        final int timeSlotLength = timeSlotConfig.getLength();

        while (benchmarkTime.isBefore(endTime)) {
            BookedTimeSlot slot = BookedTimeSlot.builder()
                    .bookingTime(LocalDateTime.now())
                    .startTime(benchmarkTime)
                    .endTime(benchmarkTime.plusMinutes(timeSlotLength)
                            .minusSeconds(1)) // Prevent overlap
                    .build();
            timeSlots.add(slot);
            benchmarkTime = benchmarkTime.plusMinutes(timeSlotLength);
        }

        return timeSlots;
    }

}
