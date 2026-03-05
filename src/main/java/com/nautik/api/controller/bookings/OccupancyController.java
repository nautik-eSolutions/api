package com.nautik.api.controller.bookings;

import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.bookings.CheckInOutDto;
import com.nautik.api.dto.bookings.UpdateArrivalStatusDto;
import com.nautik.api.dto.bookings.UpdateTimeDto;
import com.nautik.api.dto.occupancy.OccupancyDto;
import com.nautik.api.service.bookings.OccupancyService;
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

    private final OccupancyService occupancyService;


    @GetMapping("/mooring-categories/{mooringCategoryId}/dates/{startDate}/{endDate}")
    @OnlyPortAdministrators
    public ResponseEntity<OccupancyDto> getOccupancyByMooringCategoryAndDates(
            @PathVariable(name = "mooringCategoryId") Integer mooringCategoryId,
            @PathVariable(name = "startDate") String startDate,
            @PathVariable(name = "endDate") String endDate,
            Authentication authentication) {

        CustomAdminUserDetails customAdminUserDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        OccupancyDto occupancy = occupancyService.getOccupancyByMooringCategoryAndDates(mooringCategoryId, customAdminUserDetails.getPortId(), startDate, endDate);
        return ResponseEntity.ok(occupancy);
    }

    @GetMapping("/checkins/{startDate}")
    @OnlyPortAdministrators
    public ResponseEntity<List<CheckInOutDto>> getCheckInsByDate(Authentication authentication,
                                                                 @PathVariable(name = "startDate") String startDate) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        List<CheckInOutDto> checkIns = occupancyService.getCheckInsByDate(startDate, portId);

        return ResponseEntity.ok(checkIns);
    }

    @GetMapping("/checkouts/{endDate}")
    @OnlyPortAdministrators
    public ResponseEntity<List<CheckInOutDto>> getCheckOutsByDate(
            @PathVariable(name = "endDate") String endDate,
            Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        List<CheckInOutDto> checkOuts = occupancyService.getCheckOutsByDate(endDate, portId);

        return ResponseEntity.ok(checkOuts);
    }

    @PutMapping("/{checkInOutId}/arrival")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateArrivalStatus(
            @PathVariable(name = "checkInOutId") Integer checkInOutId,
            @RequestBody UpdateArrivalStatusDto dto, Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        occupancyService.updateArrivalTime(checkInOutId, dto.getHasCheckedIn(), dto.getActualTime(), portId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{checkInOutId}/departure")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateDepartureStatus(
            @PathVariable(name = "checkInOutId") Integer checkInOutId, @RequestBody UpdateArrivalStatusDto dto, Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        occupancyService.updateDepartureStatus(checkInOutId, dto.getHasCheckedIn(), dto.getActualTime(), portId);

        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{checkInOutId}/checkin-time")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateCheckInTime(
            @PathVariable(name = "checkInOutId") Integer checkInOutId, @RequestBody UpdateTimeDto dto, Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        occupancyService.updateCheckInTime(checkInOutId, dto.getTime(), portId);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{checkInOutId}/checkout-time")
    @OnlyPortAdministrators
    public ResponseEntity<Void> updateCheckOutTime(
            @PathVariable(name = "checkInOutId") Integer checkInOutId,
            @RequestBody UpdateTimeDto dto, Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        occupancyService.updateCheckOutTime(checkInOutId, dto.getTime(), portId);
        return ResponseEntity.ok().build();
    }


}
