package com.nautik.api.controller.bookings;


import com.nautik.api.domain.booking.Booking;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.dto.bookings.BookingRequestDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.repository.user.UserRepository;
import com.nautik.api.service.bookings.BookingService;
import com.nautik.api.service.jwt.JwtService;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingsController {

    private final BookingService bookingService;
    private final JwtService jwtService;

    @GetMapping("/{mooringCategoryId}/{startDate}/{endDate}")
    public List<BookingDto> getBookings(
            @PathVariable Integer mooringCategoryId,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        return bookingService.getAvailableMooringCategoriesByPortAndStartDateAndEndDate(mooringCategoryId,startDate,endDate);
    }

    @PreAuthorize("hasAnyAuthority('STAFF','PORT_ADMIN')")
    @GetMapping("ports/{portId}")
    public ResponseEntity<List<BookingOccupancyDto>> getBookingsByPortFromNow(@PathVariable Integer portId){
        return ResponseEntity.ok(bookingService.getAllBookingsByPortFromNow(portId));
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


    @GetMapping("/moorings/{mooringCategoryId}/dates/{startDate}/{endDate}")
    public List<MooringDto> getFreeMooringsByAvailabilityAndCategory(
            @PathVariable Integer mooringCategoryId,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
     return   bookingService.getAllFreeMooringsByDateAndCategory(mooringCategoryId,startDate,endDate);
    }


    @PostMapping
    public Boolean createBooking(@RequestBody BookingRequestDto bookingRequestDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader ){

        String token = authHeader.substring(7);
        String userName = jwtService.extractUsername(token);

        return bookingService.createBooking(bookingRequestDto, userName);
    }






}
