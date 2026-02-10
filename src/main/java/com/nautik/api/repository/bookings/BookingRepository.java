package com.nautik.api.repository.bookings;

import com.nautik.api.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookingRepository  extends JpaRepository<Booking, Long> {


    List<Booking> findAllByMooringMooringCategoryId(Integer mooringMooringCategoryId);

    List<Booking> findAllByMooringMooringCategoryIdAndStartDateBeforeAndEndDateAfter(Integer mooringMooringCategoryId, Date startDateBefore, Date endDateAfter);
}
