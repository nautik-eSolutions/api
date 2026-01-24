package com.nautik.api.controller.moorings;


import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.service.moorings.MooringService;
import lombok.RequiredArgsConstructor;
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
            @PathVariable Long mooringId,
            @PathVariable String portName) {
        String name = portName.replace("_", " ");
        mooringService.delete(mooringId);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{mooringId}")
    public ResponseEntity<MooringDto> updateMooring(
            @PathVariable String portName,
            @RequestBody MooringDto mooring,
            @PathVariable long mooringId
    ){
        MooringDto mooringDtp = mooringService.update(mooringId, mooring);
        return ResponseEntity.ok(mooringDtp);

    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<MooringDto>> getMooringByZoneId(
            @PathVariable Long zoneId,
            @PathVariable String portName
    ){
        List<MooringDto> moorings = mooringService.findAllByZoneId(Math.toIntExact(zoneId));
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/zone/{zoneId}/available")
    public ResponseEntity<MooringDto> getAvailableMooringsByZoneId(
            @PathVariable String portName,
            @PathVariable String zoneId){
        return ResponseEntity.ok().build();
    }


}

