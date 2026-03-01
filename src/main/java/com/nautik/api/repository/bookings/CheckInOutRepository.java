package com.nautik.api.repository.bookings;

import com.nautik.api.domain.booking.CheckInOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CheckInOutRepository extends JpaRepository<CheckInOut,Integer> {
    Optional<CheckInOut> findByBookingId(Integer bookingId);

    List<CheckInOut> findCheckInsByDateAndPort(Date date,  Integer portId);
    List<CheckInOut> findCheckOutsByDateAndPort( Date date,  Integer portId);

    List<CheckInOut> findByDateRangeAndPort(
            Date startDate,
           Date endDate,
             Integer portId
    );

    Optional<CheckInOut> findByIdAndPortId( Integer occupancyId,  Integer portId);
}
