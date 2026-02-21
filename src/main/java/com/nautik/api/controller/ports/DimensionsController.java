package com.nautik.api.controller.ports;

import com.nautik.api.dto.mooring.MooringDimensionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dimensions")
@RequiredArgsConstructor
public class DimensionsController {


    /*
    @GetMapping("/port/{portId}")
    public ResponseEntity<List<MooringDimensionDto>> getMooringDimensionsByPort(@PathVariable Integer portId){

    }

     */
}
