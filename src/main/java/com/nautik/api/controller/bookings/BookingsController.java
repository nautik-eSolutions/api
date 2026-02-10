package com.nautik.api.controller.bookings;


import com.nautik.api.domain.booking.Booking;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.service.bookings.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingsController {

    private final BookingService bookingService;

    @GetMapping("/{mooringCategoryId}/{startDate}/{endDate}")
    public List<BookingDto> getBookings(
            @PathVariable Integer mooringCategoryId,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        return bookingService.getAvailableMooringCategoriesByPortAndStartDateAndEndDate(mooringCategoryId,startDate,endDate);
    }

    @GetMapping("/dimensions/{length}/{beam}/dates/{startDate}/{endDate}")
    public List<BookingDto> getBookingsByDimensionsAndAvailability(
            @PathVariable Integer length,
            @PathVariable Integer beam,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        return bookingService.getBookingsByMooringDimensionsAndAvailability(beam,length,startDate,endDate);
    }




}
