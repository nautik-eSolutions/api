package com.nautik.api.controller.bookings;

import com.nautik.api.dto.mooring.MooringCategoryAvailabilityDto;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.service.bookings.MooringCategoryAvailabilityService;
import com.nautik.api.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final MooringCategoryAvailabilityService mooringCategoryService;

    @GetMapping("/mooring-categories/port/{portId}/dates/{startDate}/{endDate}")
    public ResponseEntity<List<MooringCategoryAvailabilityDto>> getMooringCategoriesByPortDimensionsAndAvailability(
            @PathVariable("portId") Integer portId,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate,
            @RequestParam("length") Double length,
            @RequestParam("beam") Double beam,
            @RequestParam("draft") Double draft
    ) {
        return ResponseEntity.ok(mooringCategoryService.getMooringCategoriesWithByAvailability(
                portId, length, beam, draft, startDate, endDate));
    }

    @GetMapping("/mooring-categories/{mooringCategoryId}/dates/{startDate}/{endDate}")
    public MooringCategoryAvailabilityDto getMooringCategoryByIdAndAvailability(
            @PathVariable("mooringCategoryId") Integer mooringCategoryId,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate
    ) {
        return mooringCategoryService.getMooringCategoryByIdAndAvailability(
                mooringCategoryId, startDate, endDate);
    }
}
