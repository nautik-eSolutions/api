package com.nautik.api.controller.bookings;

import com.nautik.api.configuration.PreAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.occupancy.OccupancyDto;
import com.nautik.api.service.bookings.OccupancyService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/occupancy")
@RequiredArgsConstructor
public class OccupancyController {

    private final OccupancyService occupancyService;


    @GetMapping("/mooring-categories/{mooringCategoryId}/dates/{startDate}/{endDate}")
    @OnlyPortAdministrators
    public ResponseEntity<OccupancyDto> getOccupancyByMooringCategoryAndDates(
            @PathVariable Integer mooringCategoryId,
            @PathVariable String startDate,
            @PathVariable String endDate,
            Authentication authentication
    ) {

        CustomAdminUserDetails customAdminUserDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        OccupancyDto occupancy = occupancyService.getOccupancyByMooringCategoryAndDates(
                mooringCategoryId, customAdminUserDetails.getPortId(), startDate, endDate
        );
        return ResponseEntity.ok(occupancy);
    }




}
