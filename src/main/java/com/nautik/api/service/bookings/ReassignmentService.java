package com.nautik.api.service.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.bookings.BookingReassignmentDto;
import com.nautik.api.dto.bookings.ReassignmentResultDto;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReassignmentService {

    private final MooringRepository mooringRepository;
    private final BookingRepository bookingRepository;
    private final MooringCategoryRepository mooringCategoryRepository;


    public ReassignmentResultDto reassignBookings(Integer mooringCategoryId) {
        MooringCategory category = mooringCategoryRepository.findById(mooringCategoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        List<Mooring> moorings = mooringRepository.findMooringsByMooringCategory(mooringCategoryId);

        List<Booking> bookings = bookingRepository.findByMooringCategoryAndStartDateAndStatusConfirmed(mooringCategoryId, new Date(), new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)));


        Map<Integer, Integer> newAssignments = IntervalPartitionService.reassignBookins(bookings, moorings);

        List<BookingReassignmentDto> reassignments = new ArrayList<>();
        int totalReassigned = 0;

        for (Map.Entry<Integer, Integer> entry : newAssignments.entrySet()) {
            Integer bookingId = entry.getKey();
            Integer newMooringId = entry.getValue();

            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found with"));

            Integer oldMooringId = booking.getMooring() != null ? booking.getMooring().getId() : null;

            if (oldMooringId == null || !oldMooringId.equals(newMooringId)) {
                Mooring newMooring = mooringRepository.findById(newMooringId).orElseThrow(() -> new EntityNotFoundException("Mooring not found"));

                reassignments.add(new BookingReassignmentDto(bookingId, oldMooringId, newMooringId, booking.getStartDate(), booking.getEndDate()));
                booking.setMooring(newMooring);
                bookingRepository.save(booking);
                totalReassigned++;
            }
        }

        return new ReassignmentResultDto(totalReassigned, bookings.size(), mooringCategoryId, reassignments);
    }
}
