package com.nautik.api.controller.moorings;


import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;
import com.nautik.api.service.moorings.MooringService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moorings")
@RequiredArgsConstructor

public class MooringController {

    public final MooringService mooringService;

    @GetMapping
    public ResponseEntity<List<MooringDto>> getAllMoorings(){
        List<MooringDto> moorings  = mooringService.findAll();
        return ResponseEntity.ok(moorings);
    }


    @GetMapping("/ports/{portId}")
    public ResponseEntity<List<MooringDto>>getAllMooringsByPort(@PathVariable Integer portId){

        List<MooringDto> moorings = mooringService.findAllByPortId(portId);

        return ResponseEntity.ok(moorings);
    }


    @GetMapping("/dimensions")
    public ResponseEntity<List<MooringDimensionDto>> getAllDimensions(){
        List<MooringDimensionDto> dimensions = mooringService.getAllMooringsDimensions();
        return ResponseEntity.ok(dimensions);
    }

    @GetMapping("/{mooringId}")
    public ResponseEntity<MooringDto> getMooringById(
            @PathVariable Long mooringId) {
        MooringDto mooring = mooringService.findById(Math.toIntExact(mooringId));
        return ResponseEntity.ok(mooring);
    }

    @PostMapping("/{portId}")
    public ResponseEntity<MooringDto> createMooring(
            @PathVariable Long portId,
            @RequestBody CreateMooringDto mooring
    ){
        MooringDto dto = mooringService.createMooring(portId, mooring);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);


    }

    @DeleteMapping("/{mooringId}")
    public ResponseEntity<MooringDto> deleteMooringById(
            @PathVariable Long mooringId){
        mooringService.delete(mooringId);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{mooringId}")
    public ResponseEntity<MooringDto> updateMooring(
            @RequestBody CreateMooringDto mooring,
            @PathVariable Long mooringId
    ){
        MooringCategoryDto mooringCategory = mooringService.findCategoryById(mooring.getDimensionsId(),mooring.getZoneId());

        MooringDto createMooring = new MooringDto(
                mooringId, mooring.getNumber(), mooringCategory
        );

        MooringDto mooringDtp = mooringService.update(mooringId, createMooring);
        return ResponseEntity.ok(mooringDtp);

    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<MooringDto>> getMooringByZoneId(
            @PathVariable Long zoneId
    ){
        List<MooringDto> moorings = mooringService.findAllByZoneId(Math.toIntExact(zoneId));
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/zone/{zoneId}/available")
    public ResponseEntity<List<MooringDto>> getAvailableMooringsByZoneId(
            @PathVariable Long zoneId){
        List<MooringDto> moorings = mooringService.findAllByZoneAvailable(zoneId);
        return ResponseEntity.ok(moorings);
    }





}

