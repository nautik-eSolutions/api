package com.nautik.api.service.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IntervalPartitionService {

    public static Mooring assignMooring(Booking newBooking, List<Booking> existingBookings, List<Mooring> availableMoorings) {

        Map<Integer, MooringResource> mooringResources = new HashMap<>();

        for (Mooring mooring : availableMoorings) {
            mooringResources.put(mooring.getId(), new MooringResource(mooring));
        }

        for (Booking existingBooking : existingBookings) {
            if (existingBooking.getMooring() != null) {
                Integer mooringId = existingBooking.getMooring().getId();
                if (mooringResources.containsKey(mooringId)) {
                    mooringResources.get(mooringId).addBooking(existingBooking);
                }
            }
        }

        List<MooringResource> sortedResources = mooringResources.values().stream().sorted(Comparator.comparing(MooringResource::getLastEndTime)).toList();

        for (MooringResource resource : sortedResources) {
            if (resource.isAvailableFor(newBooking)) {
                return resource.getMooring();
            }
        }

        return null;
    }

    static public Map<Integer, Integer> reassignBookins(List<Booking> bookings, List<Mooring> moorings) {

        Map<Integer, Integer> newAssignments = new HashMap<>();
        List<Booking> sortedBookings = bookings.stream().sorted(Comparator.comparing(Booking::getStartDate)).toList();
        List<MooringResource> resources = moorings.stream().map(MooringResource::new).toList();

        for (Booking booking : sortedBookings) {
            boolean assigned = false;
            for (MooringResource resource : resources) {
                if (resource.isAvailableFor(booking)) {
                    resource.addBooking(booking);
                    newAssignments.put(booking.getId(), resource.getMooring().getId());
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                if (booking.getMooring() != null) {
                    newAssignments.put(booking.getId(), booking.getMooring().getId());
                }
            }
        }

        return newAssignments;
    }



}