package com.nautik.api.controller.bookings;

import com.nautik.api.dto.mooring.MooringCategoryAvailabilityDto;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.service.bookings.MooringCategoryAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final MooringCategoryAvailabilityService mooringCategoryService;


    @GetMapping("/mooring-categories/port/{portId}/dimensions/{length}/{beam}/{draft}/dates/{startDate}/{endDate}")
    public ResponseEntity<List<MooringCategoryAvailabilityDto>> getMooringCategoriesByPortDimensionsAndAvailability (
            @PathVariable Integer portId,
            @PathVariable Double length,
            @PathVariable Double beam,
            @PathVariable Double draft,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        System.out.println("1111111111111111111111111");
        return ResponseEntity.ok(mooringCategoryService.getMooringCategoriesbyPortDimensionsAndAvailability(
                portId, length, beam,draft,  startDate, endDate));
    }


    @GetMapping("/mooring-categories/{mooringCategoryId}/dates/{startDate}/{endDate}")
    public MooringCategoryAvailabilityDto getMooringCategoryByIdAndAvailability (
            @PathVariable Integer mooringCategoryId,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        return mooringCategoryService.getMooringCategoryByIdAndAvailability(
                mooringCategoryId, startDate, endDate);
    }






}
