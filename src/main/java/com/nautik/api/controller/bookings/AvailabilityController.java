package com.nautik.api.controller.bookings;

import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.service.bookings.MooringCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final MooringCategoryService mooringCategoryService;

    @GetMapping("/categories/{portId}")
    public List<MooringCategoryDto> getMooringCategoriesByPortId(@PathVariable Integer portId){
        return mooringCategoryService.getAllMooringCategoriesByPort(portId);
    }
}
