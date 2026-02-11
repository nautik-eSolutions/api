package com.nautik.api.controller.bookings;

import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.service.bookings.MooringCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final MooringCategoryService mooringCategoryService;


    @GetMapping("/mooring-categories/port/{portId}/dimensions/{length}/{beam}/dates/{startDate}/{endDate}")
    public List<MooringCategoryDto> getMooringCategoriesByPortDimensionsAndAvailability (
            @PathVariable Integer portId,
            @PathVariable Integer length,
            @PathVariable Integer beam,
            @PathVariable String startDate,
            @PathVariable String endDate
    ){
        return mooringCategoryService.getMooringCategoriesbyPortDimensionsAndAvailability(
                portId, length, beam, startDate, endDate);
    }



}
