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
@RequestMapping("/api/v1/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping("/port/{portId}")
    public ResponseEntity<List<ZoneDto>> getZonesByPort(
            @PathVariable Long portId
    ) {
        List<ZoneDto> zoneList = zoneService.findByPort(portId);
        return ResponseEntity.ok(zoneList);
    }

    @GetMapping("/{zoneId}")
    public ResponseEntity<ZoneDto> getZoneById(
            @PathVariable Integer zoneId
    ) {
        ZoneDto zone = zoneService.findById(zoneId);
        return ResponseEntity.ok(zone);
    }

    @PostMapping("/port/{portId}")
    public ResponseEntity<ZoneDto> createZone(
            @RequestBody CreateZoneDto dto,
            @PathVariable Integer portId
    ) {
        ZoneDto zone = zoneService.create(portId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(zone);
    }

    @PutMapping("/{zoneId}/port/{portId}")
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
            @PathVariable Long zoneId
    ) {
        zoneService.delete(Math.toIntExact(zoneId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

