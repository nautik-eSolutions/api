package com.nautik.api.controller.bookings;


import com.nautik.api.configuration.PreAuthorizeConfig.OnlyAdministrators;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.dto.bookings.BookingRequestDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.service.bookings.BookingService;
import com.nautik.api.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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

    @OnlyAdministrators
    @GetMapping("ports/{portId}")
    public ResponseEntity<List<BookingOccupancyDto>> getBookingsByPortFromNow(@PathVariable Integer portId){
        return ResponseEntity.ok(bookingService.getAllBookingsByPortFromNow(portId));
    }

    @OnlyAdministrators
    @GetMapping("/dimensions/{length}/{beam}/dates/{startDate}/{endDate}")
    public List<BookingDto> getBookingsByDimensionsAndAvailability(
            @PathVariable Integer length,
            @PathVariable Integer beam,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        return bookingService.getBookingsByMooringDimensionsAndAvailability(beam,length,startDate,endDate);
    }

    @PreAuthorize("hasAnyAuthority('STAFF','PORT_ADMIN','ADMIN_COMPANY')")
    @GetMapping("/moorings/{mooringCategoryId}/dates/{startDate}/{endDate}")
    public List<MooringDto> getFreeMooringsByAvailabilityAndCategory(
            @PathVariable Integer mooringCategoryId,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
     return   bookingService.getAllFreeMooringsByDateAndCategory(mooringCategoryId,startDate,endDate);
    }

    @GetMapping("/moorings/{mooringId}")
    public List<BookingDto> getBookingsByMooringId(
            @PathVariable Integer mooringId
    ){
        return bookingService.getAllBookingsByMooringId(mooringId);
    }




    @PostMapping
    public Boolean createBooking(@RequestBody BookingRequestDto bookingRequestDto,
                                 Authentication  authentication ){
        User userDetails = (User) authentication.getPrincipal();

        return bookingService.createBooking(bookingRequestDto, Integer.parseInt(userDetails.getUsername()));
    }






}
