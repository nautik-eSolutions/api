package com.nautik.api.controller.bookings;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class OccupancyController {



    public ResponseEntity<List<OccupancyDto>> getOccupancyByMooringCategoryAndDates(){

    }




}
