package com.nautik.api.repository.moorings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MooringRepository extends JpaRepository<Mooring, Integer> {

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

    @Query("select m from Mooring m inner join MooringCategory mc on m.mooringCategory = mc where mc.id = ?1  ")
    List<Mooring> findMooringsByMooringCategory(Integer mooringId);

    @Query("select COUNT (m) from Mooring m where m.mooringCategory.id = ?1 and m not in ( select m1 from Mooring m1 inner join Booking b on b.mooring = m1 where b.startDate < ?3 and b.endDate > ?2 )")
    Integer findNumberOfFreeMooringsByCategory(Integer mooringCategory, Date startDate, Date endDate);

    @Query("select COUNT (m) from Mooring m where m.mooringCategory.id = ?1")
    Integer findNumberMooringsByCategory(Integer mooringCategory);

    @Query("select m from Mooring m inner join MooringCategory mc on m.mooringCategory = m inner join Zone z on mc.zone = z inner join Port p on z.port = p where p.id = ?1")
    List<Mooring> findMooringsByPortId(Integer portId);


    List<Mooring> findByMooringCategoryId(Integer mooringCategoryId);

}