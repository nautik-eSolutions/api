package com.nautik.api.service.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringResource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntervalPartitionService {

    public Mooring assignMooring(Booking newBooking, List<Booking> existingBookings, List<Mooring> availableMoorings) {

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


}