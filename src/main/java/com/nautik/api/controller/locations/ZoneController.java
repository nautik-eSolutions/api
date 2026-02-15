package com.nautik.api.controller.locations;


import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.dto.location.create.CreateZoneDto;
import com.nautik.api.service.location.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ports/{portId}/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping
    public ResponseEntity<List<ZoneDto>> getZonesByPort(
            @PathVariable Long portId
    ) {
        List<ZoneDto> zoneList = zoneService.findByPort(portId);
        return ResponseEntity.ok(zoneList);
    }

    @GetMapping("/{zoneId}")
    public ResponseEntity<ZoneDto> getZoneById(
            @PathVariable Long zoneId,
            @PathVariable Long portId
    ) {
        ZoneDto zone = zoneService.findById(Math.toIntExact(zoneId), portId);
        return ResponseEntity.ok(zone);
    }

    @PostMapping
    public ResponseEntity<ZoneDto> createZone(
            @PathVariable Long portId,
            @RequestBody CreateZoneDto dto
    ) {
        ZoneDto zone = zoneService.create(portId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(zone);
    }

    @PutMapping("/{zoneId}")
    public ResponseEntity<ZoneDto> updateZone(
            @PathVariable Long zoneId,
            @RequestBody CreateZoneDto dto,
            @PathVariable Long portId
    ) {
        ZoneDto zone = zoneService.update(Math.toIntExact(zoneId),dto, portId);
        return ResponseEntity.ok(zone);
    }

    @DeleteMapping("/{zoneId}")
    public ResponseEntity<Void> deleteZone(
            @PathVariable Long portId,
            @PathVariable Long zoneId
    ) {
        zoneService.delete(Math.toIntExact(zoneId));
        return ResponseEntity.ok().build();
    }
}

