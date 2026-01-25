package com.nautik.api.controller.moorings;


import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
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
    private final MooringCategoryRepository mooringCategoryRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<MooringDto>> getAllMoorings(){
        List<MooringDto> moorings  = mooringService.findAll();
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/{mooringId}")
    public ResponseEntity<MooringDto> getMooringById(
            @PathVariable Long mooringId) {
        MooringDto mooring = mooringService.findById(Math.toIntExact(mooringId));
        return ResponseEntity.ok(mooring);
    }

    @PostMapping("/{portName}")
    public ResponseEntity<MooringDto> createMooring(
            @PathVariable String portName,
            @RequestBody CreateMooringDto mooring
    ){
        String name = portName.replace("_", " ");
        MooringDto dto = mooringService.createMooring(name, mooring);
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
        MooringCategoryDto mooringCategory = modelMapper.map(
                mooringCategoryRepository.findById(mooring.getCategoryId()).orElseThrow(), MooringCategoryDto.class);

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
    public ResponseEntity<MooringDto> getAvailableMooringsByZoneId(
            @PathVariable String zoneId){
        return ResponseEntity.ok().build();
    }


}

