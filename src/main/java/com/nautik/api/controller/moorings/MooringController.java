package com.nautik.api.controller.moorings;


import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.dto.mooring.create.MooringDimensionCreateDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;
import com.nautik.api.service.moorings.MooringService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moorings")
@RequiredArgsConstructor

public class MooringController {

    public final MooringService mooringService;

    @GetMapping
    public ResponseEntity<List<MooringDto>> getAllMoorings() {
        List<MooringDto> moorings = mooringService.findAll();
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/category/{mooringCategoriesId}")
    public ResponseEntity<List<MooringDto>> getAllMooringByCategoryId(@PathVariable Integer mooringCategoriesId){
        List<MooringDto> moorings = mooringService.findByMooringCategoryId(mooringCategoriesId);
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/{mooringId}")
    public ResponseEntity<MooringDto> getMooringById(@PathVariable Integer mooringId) {
        MooringDto mooring = mooringService.findById(mooringId);
        return ResponseEntity.ok(mooring);
    }



    @GetMapping("/ports/{portId}")
    public ResponseEntity<List<MooringDto>> getAllMooringsByPort(
            @PathVariable Integer portId) {

        List<MooringDto> moorings = mooringService.findAllByPortId(portId);

        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/ports/{portId}/dimensions")
    public ResponseEntity<List<MooringDimensionDto>> getAllDimensionsByPort(
            @PathVariable Integer portId
    ) {
        List<MooringDimensionDto> dimensions = mooringService.getAllMooringsDimensions();
        return ResponseEntity.ok(dimensions);
    }





    @PostMapping("/category/{categoryId}")
    public ResponseEntity<MooringDto> createMooring(@PathVariable Integer categoryId, @RequestBody CreateMooringDto mooring) {
        MooringDto dto = mooringService.createMooring(categoryId, mooring);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);


    }

    @DeleteMapping("/{mooringId}")
    public ResponseEntity<MooringDto> deleteMooringById(@PathVariable Integer mooringId) {
        mooringService.delete(mooringId);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{mooringId}")
    public ResponseEntity<MooringDto> updateMooring(@RequestBody CreateMooringDto mooring, @PathVariable Integer mooringId) {

        MooringDto mooringDtp = mooringService.update(mooringId, mooring);
        return ResponseEntity.ok(mooringDtp);

    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<MooringDto>> getMooringByZoneId(@PathVariable Long zoneId) {
        List<MooringDto> moorings = mooringService.findAllByZoneId(Math.toIntExact(zoneId));
        return ResponseEntity.ok(moorings);
    }



}

