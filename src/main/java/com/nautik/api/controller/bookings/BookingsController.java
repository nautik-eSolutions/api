package com.nautik.api.controller.bookings;


import com.nautik.api.configuration.preAuthorizeConfig.OnlyAdministrators;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.dto.bookings.ReassignmentResultDto;
import com.nautik.api.service.bookings.BookingService;
import com.nautik.api.service.bookings.ReassignmentService;
import com.nautik.api.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingsController {

    private final BookingService bookingService;
    private final JwtService jwtService;
    private final ReassignmentService reassignmentService;


    @OnlyAdministrators
    @GetMapping("ports/{portId}")
    public ResponseEntity<List<BookingOccupancyDto>> getBookingsByPortFromNow(
            @PathVariable(name = "portId") Integer portId){
        return ResponseEntity.ok(bookingService.getAllBookingsByPortFromNow(portId));
    }

    @OnlyAdministrators
    @GetMapping("/dimensions/{length}/{beam}/dates/{startDate}/{endDate}")
    public List<BookingDto> getBookingsByDimensionsAndAvailability(
            @PathVariable(name = "length") Integer length,
            @PathVariable(name="beam") Integer beam,
            @PathVariable(name="startDate") String startDate,
            @PathVariable(name="endDate") String endDate
    ){
        return bookingService.getBookingsByMooringDimensionsAndAvailability(beam,length,startDate,endDate);
    }


    @GetMapping("/moorings/{mooringId}")
    public List<BookingDto> getBookingsByMooringId(
            @PathVariable(name = "mooringId") Integer mooringId
    ){
        return bookingService.getAllBookingsByMooringId(mooringId);
    }



    /*
    @PostMapping
    public Boolean createBooking(@RequestBody BookingRequestDto bookingRequestDto, Authentication  authentication ){
        User userDetails = (User) authentication.getPrincipal();

        return bookingService.createBooking(bookingRequestDto, Integer.parseInt(userDetails.getUsername()));
    }
    */






    @PostMapping("/reassignment/mooring-category/{mooringCategoryId}")
    public ResponseEntity<ReassignmentResultDto>reassignBookingsByMooringCategory(
            @PathVariable(name = "mooringCategoryId") Integer mooringCategoryId){
        return ResponseEntity.ok(reassignmentService.reassignBookings(mooringCategoryId));
    }


}
