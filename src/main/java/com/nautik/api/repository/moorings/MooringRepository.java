package com.nautik.api.repository.moorings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface MooringRepository extends JpaRepository<Mooring, Long> {

    List<Mooring> findAllByMooringCategory_Zone_Port_NameIgnoreCase(String mooringCategoryZonePortName);

    List<Mooring> findAllByMooringCategory_Zone_Id(long mooringCategoryZoneId);


    List<Mooring> findAllByMooringCategoryZonePortId(Integer mooringCategoryZonePortId);

    List<Mooring> findAllByMooringCategoryId(Integer mooringCategoryId);

    @Query("select m from Mooring m inner join Booking b on b.mooring = m where b in ?1")
    List<Mooring> findAllByBookingsIn(List<Booking> bookings);

    @Query("select m from Mooring m where m not in (select m1 from Mooring m1 inner join Booking b on b.mooring = m1 where b in ?1)")
    List<Mooring> findMooringNotInBookings( List<Booking> bookings);



    @Query("select m from Mooring m where m.mooringCategory.id = ?1 and m not in ( select m1 from Mooring m1 inner join Booking b on b.mooring = m1 where b.startDate < ?3 and b.endDate > ?2 )")
    List<Mooring> findFreeMooringsByCategory(Integer mooringCategory, Date startDate, Date endDate);

}