package com.nautik.api.repository.bookings;

import com.nautik.api.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository  extends JpaRepository<Booking, Long> {

}
