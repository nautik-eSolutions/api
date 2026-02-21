package com.nautik.api.controller.ports;

import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.create.MooringDimensionCreateDto;
import com.nautik.api.service.port.MooringDimensionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dimensions")
@RequiredArgsConstructor
public class DimensionsController {

    private final MooringDimensionsService mooringDimensionsService;

    @GetMapping("/port/{portId}")
    public ResponseEntity<List<MooringDimensionDto>> getMooringDimensionsByPort(@PathVariable Integer portId){
        return ResponseEntity.ok(mooringDimensionsService.getAllMooringsDimensionsByPort(portId));
    }

    @PatchMapping("/{dimensionId}")
    public ResponseEntity<MooringDimensionDto> updateMooringDimension(@PathVariable Integer dimensionId, @RequestBody MooringDimensionCreateDto dimensionCreateDto){
        return ResponseEntity.ok(mooringDimensionsService.updateMooringDimension(dimensionId,dimensionCreateDto));
    }
    @PostMapping("/port/{portId}")
    public ResponseEntity<MooringDimensionDto> createMooringDimension(@PathVariable Integer portId, @RequestBody MooringDimensionCreateDto dimensionCreateDto){
        return ResponseEntity.ok(mooringDimensionsService.createMooringDimension(portId, dimensionCreateDto));
    }
    @DeleteMapping("/{dimensionId}")
    public ResponseEntity<Void> createMooringDimension(@PathVariable Integer dimensionId ){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mooringDimensionsService.createMooringDimension(dimensionId));
    }
}
}
