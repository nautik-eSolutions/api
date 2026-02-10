package com.nautik.api.repository.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.MooringCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookingRepository  extends JpaRepository<Booking, Long> {


    List<Booking> findAllByMooringMooringCategoryId(Integer mooringMooringCategoryId);

    List<Booking> findAllByMooringMooringCategoryIdAndStartDateBeforeAndEndDateAfter(Integer mooringMooringCategoryId, Date startDateBefore, Date endDateAfter);

    List<Booking> findAllByMooringMooringCategoryDimensionsMaxLengthGreaterThanEqualAndMooringMooringCategoryDimensionsMaxBeamGreaterThanEqualAndStartDateBeforeAndEndDateAfter(Integer mooringMooringCategoryDimensionsMaxLengthIsGreaterThan, Integer mooringMooringCategoryDimensionsMaxBeamIsGreaterThan, Date startDateBefore, Date endDateAfter);


    @Query("select b from Booking b " +
            "inner join Mooring m on b.mooring = m " +
            "inner join MooringCategory mc on m.mooringCategory =" +
            " mc where mc in ?1 and b.startDate < ?3 and b.endDate > ?2")
    List<Booking> findByMooringCategoriesAndAvailability(List<MooringCategory>mooringCategories, Date startDate, Date endDate);
}
