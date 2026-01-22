package com.nautik.api.controller.locations;


import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.service.location.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ports/{portName}/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping
    public ResponseEntity<List<ZoneDto>> getZonesByPort(
            @PathVariable String portName
    ) {
        List<ZoneDto> zoneList = zoneService.findByPort(portName);
        return ResponseEntity.ok(zoneList);
    }

    @GetMapping("/{zoneId}")
    public ResponseEntity<ZoneDto> getZoneById(
            @PathVariable Long zoneId
    ) {
        ZoneDto zone = zoneService.findById(Math.toIntExact(zoneId));
        return ResponseEntity.ok(zone);
    }

    @PostMapping
    public ResponseEntity<ZoneDto> createZone(
            @PathVariable String portName,
            @RequestBody ZoneDto dto
    ) {
        ZoneDto zone = zoneService.create(portName, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(zone);
    }

    @PutMapping("/{zoneId}")
    public ResponseEntity<ZoneDto> updateZone(
            @PathVariable Long zoneId,
            @RequestBody ZoneDto dto,
            @PathVariable String portName
    ) {
        ZoneDto zone = zoneService.update(Math.toIntExact(zoneId),dto);
        return ResponseEntity.ok(zone);
    }

    @DeleteMapping("/{zoneId}")
    public ResponseEntity<Void> deleteZone(
            @PathVariable String portName,
            @PathVariable Long zoneId
    ) {
        zoneService.delete(Math.toIntExact(zoneId));
        return ResponseEntity.ok().build();
    }
}

