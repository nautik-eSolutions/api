package com.nautik.api.repository.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.MooringCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Integer> {


    List<Booking> findAllByMooringMooringCategoryId(Integer mooringMooringCategoryId);

    List<Booking> findAllByMooringMooringCategoryIdAndStartDateBeforeAndEndDateAfter(Integer mooringMooringCategoryId, Date startDateBefore, Date endDateAfter);


    @Query("select p.id from Port p inner join Booking b on b.mooring.mooringCategory.zone.port = p where b.id = ?1")
    Integer getPortIdByBookingId(Integer bookingId);

    @Query("select b from Booking b " +
            "inner join Mooring m on b.mooring = m " +
            "inner join MooringCategory mc on m.mooringCategory =" +
            " mc where mc in ?1 and b.startDate < ?3 and b.endDate > ?2")
    List<Booking> findByMooringCategoriesAndAvailability(List<MooringCategory>mooringCategories, Date startDate, Date endDate);


    @Query(" select b from Booking b where b.mooring.mooringCategory.id = ?1 and b.startDate < ?3 and b.endDate > ?2 and (b.status = 'PAID' or b.status = 'PENDING')")
    List<Booking> findByMooringCategoryAndStartDateAndStatusConfirmed(Integer mooringCategoryId, Date startDate, Date endDate);

    List<Booking> findAllByMooringMooringCategoryZonePortIdAndStartDateAfter(Integer mooringMooringCategoryZonePortId, Date startDateAfter);

    List<Booking> findAllByMooringId(Integer mooringId);

    Optional<Booking> findByOrderNumber(String orderNumber);

    List<Booking> findAllByMooringMooringCategoryZonePortId(Integer mooringMooringCategoryZonePortId);

    @Query("SELECT b FROM Booking b INNER JOIN  b.boat boat INNER JOIN boat.user WHERE b.mooring.mooringCategory.zone.port.id = ?1")
    Page<Booking> findByPortId(Integer portId, Pageable pageable);




}
