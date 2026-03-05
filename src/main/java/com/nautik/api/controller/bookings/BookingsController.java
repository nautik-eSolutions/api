package com.nautik.api.controller.bookings;


import com.nautik.api.configuration.preAuthorizeConfig.OnlyAdministrators;
import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.dto.bookings.ReassignmentResultDto;
import com.nautik.api.service.bookings.BookingService;
import com.nautik.api.service.bookings.ReassignmentService;
import com.nautik.api.service.jwt.JwtService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingsController {

    private final BookingService bookingService;
    private final JwtService jwtService;
    private final ReassignmentService reassignmentService;


    @OnlyPortAdministrators
    @GetMapping
    public ResponseEntity<Page<BookingDto>> getBookings(
            @AuthenticationPrincipal CustomAdminUserDetails currentUser,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Integer portId = currentUser.getPortId();

        Page<BookingDto> bookings = bookingService.getBookingsByPort(portId, search, pageable);
        return ResponseEntity.ok(bookings);
    }


    @OnlyAdministrators
    @GetMapping("ports/{portId}")
    public ResponseEntity<List<BookingOccupancyDto>> getBookingsByPortFromNow(
            @PathVariable(name = "portId") Integer portId){
        return ResponseEntity.ok(bookingService.getAllBookingsByPortFromNow(portId));
    }


    @OnlyPortAdministrators
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(
            @AuthenticationPrincipal CustomAdminUserDetails currentUser,
            @PathVariable Integer bookingId) {
        Integer portId = currentUser.getPortId();
        BookingDto booking = bookingService.getBookingById(bookingId,portId);

        return ResponseEntity.ok(booking);
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
