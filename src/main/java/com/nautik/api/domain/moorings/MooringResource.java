package com.nautik.api.domain.moorings;


import com.nautik.api.domain.booking.Booking;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
public  class MooringResource {
    private Mooring mooring;
    private List<Booking> bookings;

    public MooringResource(Mooring mooring) {
        this.mooring = mooring;
        this.bookings = new ArrayList<>();
    }


    public boolean isAvailableFor(Booking newBooking) {
        for (Booking existing : bookings) {
            if (overlaps(newBooking, existing)) {
                return false;
            }
        }
        return true;
    }
    private boolean overlaps(Booking b1, Booking b2) {
        return b1.getStartDate().before(b2.getEndDate()) && b2.getStartDate().before(b1.getEndDate());
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        bookings.sort(Comparator.comparing(Booking::getStartDate));
    }
    public Date getLastEndTime() {
        if (bookings.isEmpty()) {
            return new Date(0);
        }
        return bookings.stream().map(Booking::getEndDate)
                .max(Comparator.naturalOrder())
                .orElse(new Date(0));
    }



}

