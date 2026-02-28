package com.nautik.api.service.bookings;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.exceptions.ForbiddenException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.occupancy.OccupancyDto;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OccupancyService {

    private final PortRepository portRepository;
    private final BookingRepository bookingRepository;
    private final MooringRepository mooringRepository;
    private final MooringCategoryRepository mooringCategoryRepository;
    private final ModelMapper modelMapper;

    public OccupancyDto getOccupancyByMooringCategoryAndDates(
            Integer mooringCategoryId, Integer portId, String stringStartDate, String stringEndDate
    ) {
        validateOwnerShip(portId, mooringCategoryId);

        Date startDate = parseDate(stringStartDate);
        Date endDate = parseDate(stringEndDate);

        List<Booking> bookings = bookingRepository
                .findByMooringCategoryAndStartDateAndStatusConfirmed(mooringCategoryId, startDate, endDate);

        List<Mooring> moorings = mooringRepository.findAllByMooringCategoryId(mooringCategoryId);

        List<BookingDto> bookingDtos = bookings.stream()
                .map(b -> modelMapper.map(b, BookingDto.class))
                .toList();

        List<MooringDto> mooringDtos = moorings.stream()
                .map(m -> modelMapper.map(m, MooringDto.class))
                .toList();

        OccupancyDto occupancyDto = new OccupancyDto();
        occupancyDto.setBookings(bookingDtos);
        occupancyDto.setMoorings(mooringDtos);
        return occupancyDto;
    }


    private void validateOwnerShip(Integer portId , Integer mooringCategoryId){
        Port port = portRepository.findById(portId).orElseThrow(
                ()-> new EntityNotFoundException("Port not found")
        );

        MooringCategory mooringCategory = mooringCategoryRepository.findById(mooringCategoryId).orElseThrow(
                ()->new EntityNotFoundException("Mooring category not found")
        );

        Integer mooringCategoryPortId = mooringCategory.getZone().getPort().getId();
        if (!port.getId().equals(mooringCategoryPortId)) {
            throw new ForbiddenException("Mooring category does not belong to this port");
        }

    }


    private Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected dd-MM-yyyy, got: " + dateString);
        }
    }
}
