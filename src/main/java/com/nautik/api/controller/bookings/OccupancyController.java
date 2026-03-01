package com.nautik.api.controller.bookings;

import com.nautik.api.configuration.PreAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/occupancy")
@RequiredArgsConstructor
public class OccupancyController {

    private final CheckInOutService checkInOutService;

    @GetMapping("/checkins")
    @OnlyPortAdministrators
    public ResponseEntity<List<CheckInOutDto>> getCheckInsByDate(
            @RequestParam String date,
            Authentication authentication
    ) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        List<CheckInOutDto> checkIns = checkInOutService.getCheckInsByDate(parsedDate, portId);

        return ResponseEntity.ok(checkIns);
    }

    @GetMapping("/checkouts")
    @OnlyPortAdministrators
    public ResponseEntity<List<CheckInOutDto>> getCheckOutsByDate(
            @RequestParam String date,
            Authentication authentication
    ) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        List<CheckInOutDto> checkOuts = checkInOutService.getCheckOutsByDate(parsedDate, portId);

        return ResponseEntity.ok(checkOuts);
    }

    @PatchMapping("/{checkInOutId}/arrival")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateArrivalStatus(
            @PathVariable Integer checkInOutId,
            @RequestBody UpdateArrivalStatusDto dto,
            Authentication authentication
    ) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        checkInOutService.updateArrivalStatus(
                checkInOutId,
                dto.getHasArrived(),
                dto.getActualTime(),
                portId
        );

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{checkInOutId}/departure")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateDepartureStatus(
            @PathVariable Integer checkInOutId,
            @RequestBody UpdateArrivalStatusDto dto,
            Authentication authentication
    ) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        checkInOutService.updateDepartureStatus(
                checkInOutId,
                dto.getHasArrived(),
                dto.getActualTime(),
                portId
        );

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{checkInOutId}/checkin-time")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateCheckInTime(
            @PathVariable Integer checkInOutId,
            @RequestBody UpdateTimeDto dto,
            Authentication authentication
    ) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        checkInOutService.updateCheckInTime(checkInOutId, dto.getTime(), portId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{checkInOutId}/checkout-time")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateCheckOutTime(
            @PathVariable Integer checkInOutId,
            @RequestBody UpdateTimeDto dto,
            Authentication authentication
    ) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        checkInOutService.updateCheckOutTime(checkInOutId, dto.getTime(), portId);
        return ResponseEntity.ok().build();
    }
}