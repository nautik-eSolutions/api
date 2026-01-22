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
@RequestMapping("/api/v1/moorings/")
@RequiredArgsConstructor

public class MooringController {

    public final MooringService mooringService;

    @GetMapping
    public ResponseEntity<List<MooringDto>> getAllMoorings(
    ) {
        List<MooringDto> moorings  = mooringService.findAll();
        return ResponseEntity.ok(moorings);
    }

    @GetMapping("/{mooringId}")
    public ResponseEntity<MooringDto> getMooringById(
            @PathVariable Long mooringId
    ) {
        MooringDto zone = mooringService.findById(Math.toIntExact(mooringId));
        return ResponseEntity.ok(zone);
    }
}
