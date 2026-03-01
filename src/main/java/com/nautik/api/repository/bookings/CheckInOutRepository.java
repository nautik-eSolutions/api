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

    @Query("select cio from CheckInOut cio " +
            "inner join Booking b on cio.booking =b " +
            "inner join Mooring m on b.mooring = m " +
            "inner join MooringCategory mc on m.mooringCategory = mc " +
            "inner join Zone z on mc.zone = z " +
            "inner join Port p on z.port = p " +
            "where extract(DATE from b.startDate) = extract(DATE from ?1)  and p.id = ?2")
    List<CheckInOut> findCheckInsByDateAndPort(Date date,  Integer portId);

    @Query("select cio from CheckInOut cio " +
            "inner join Booking b on cio.booking =b " +
            "inner join Mooring m on b.mooring = m " +
            "inner join MooringCategory mc on m.mooringCategory = mc " +
            "inner join Zone z on mc.zone = z " +
            "inner join Port p on z.port = p " +
            "where extract(DATE from b.endDate) = extract(DATE from ?1)  and p.id = ?2")
    List<CheckInOut> findCheckOutsByDateAndPort( Date date,  Integer portId);
}
