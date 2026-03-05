package com.nautik.api.controller.bookings;


import com.nautik.api.configuration.preAuthorizeConfig.OnlyAdministrators;
import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.domain.booking.BookingStatus;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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
    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto> updateBookingStatus(
            @AuthenticationPrincipal CustomAdminUserDetails currentUser,
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        if (!request.containsKey("status")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status field is required");
        }
        BookingStatus newStatus;

        try {
            newStatus = BookingStatus.valueOf(request.get("status").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }


        Integer portId = currentUser.getPortId();

        BookingDto updated = bookingService.updateBookingStatus(id, newStatus,portId);
        return ResponseEntity.ok(updated);
    }

    @OnlyPortAdministrators
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(
            @AuthenticationPrincipal CustomAdminUserDetails currentUser,
            @PathVariable Integer bookingId) {

        Integer portId = currentUser.getPortId();
        bookingService.cancelBooking(bookingId,portId);
        return ResponseEntity.noContent().build();
    }


    @OnlyPortAdministrators
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(
            Authentication authentication ,
            @PathVariable Integer bookingId) {

        CustomAdminUserDetails currentUser = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = currentUser.getPortId();
        BookingDto booking = bookingService.getBookingById(bookingId,portId);

        return ResponseEntity.ok(booking);
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
