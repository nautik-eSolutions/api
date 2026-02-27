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



}

