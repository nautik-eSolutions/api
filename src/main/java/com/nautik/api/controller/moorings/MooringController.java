package com.nautik.api.controller.moorings;


import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.service.moorings.MooringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/{portName}/moorings")
@RequiredArgsConstructor

public class MooringController {

    public final MooringService mooringService;

    @GetMapping
    public ResponseEntity<List<MooringDto>> getAllMoorings(

            @PathVariable String portName) {
        List<MooringDto> moorings  = mooringService.findAllByPort(portName);
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/{mooringId}")
    public ResponseEntity<MooringDto> getMooringById(
            @PathVariable Long mooringId,
            @PathVariable String portName) {
        MooringDto zone = mooringService.findById(Math.toIntExact(mooringId));
        return ResponseEntity.ok(zone);
    }

    @PostMapping()
    public ResponseEntity<MooringDto> createMooring(
            @PathVariable String portName,
            @RequestBody MooringDto mooring
    ){
        MooringDto dto = mooringService.createMooring(portName, mooring);

        return ResponseEntity.status(HttpStatus.CREATED).body(mooring);


    }
}
