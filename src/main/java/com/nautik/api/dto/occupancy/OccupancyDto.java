package com.nautik.api.dto.occupancy;

import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.mooring.MooringDto;
import lombok.Data;

import java.util.List;

@Data
public class OccupancyDto {
    private List<BookingDto> bookings;
    private List<MooringDto> moorings;
}
