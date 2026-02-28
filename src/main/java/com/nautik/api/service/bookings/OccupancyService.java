package com.nautik.api.service.bookings;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.occupancy.OccupancyDto;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupancyService {

    private BookingRepository bookingRepository;
    private MooringRepository mooringRepository;
    private MooringCategoryRepository mooringCategoryRepository;

    public List<OccupancyDto> getOccupancyByMooringCategoryAndDates(
            Integer mooringCategoryId, Integer portId ,String stringStartDate, String stringEndDate
    ){

    }

}
