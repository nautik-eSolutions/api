package com.nautik.api.controller.locations;


import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.service.location.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ports/{portId}/zones")
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
            @PathVariable Long portId,
            @PathVariable Long zoneId
    ) {
        ZoneDto zone = zoneService.findById(portId,zoneId);
        return ResponseEntity.ok(zone);
    }

    @PostMapping
    public ResponseEntity<ZoneDto> createZone(
            @PathVariable Long portId,
            @RequestBody ZoneDto dto
    ) {
        ZoneDto zone = zoneService.create(portId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(zone);
    }

    @PutMapping("/{zoneId}")
    public ResponseEntity<ZoneDto> updateZone(
            @PathVariable Long portId,
            @PathVariable Long zoneId,
            @RequestBody ZoneDto dto
    ) {
        ZoneDto zone = zoneService.update(portId,zoneId,dto);
        return ResponseEntity.ok(zone);
    }

    @DeleteMapping("/{zoneId}")
    public ResponseEntity<Void> deleteZone(
            @PathVariable Long portId,
            @PathVariable Long zoneId
    ) {
        zoneService.delete(portId, zoneId);
        return ResponseEntity.ok().build();
    }
}

