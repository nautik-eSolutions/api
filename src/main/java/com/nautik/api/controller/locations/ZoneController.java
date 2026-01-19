package com.nautik.api.controller.locations;


import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.service.location.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ports/{portId}/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping
    public List<ZoneDto> getZonesByPort(
            @PathVariable Long portId
    ) {
        return zoneService.findByPort(portId);
    }

    @GetMapping("/{zoneId}")
    public ZoneDto getZoneById(
            @PathVariable Long portId,
            @PathVariable Long zoneId
    ) {
        return zoneService.findById(portId, zoneId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneDto createZone(
            @PathVariable Long portId,
            @RequestBody ZoneDto dto
    ) {
        return zoneService.create(portId, dto);
    }

    @PutMapping("/{zoneId}")
    public ZoneDto updateZone(
            @PathVariable Long portId,
            @PathVariable Long zoneId,
            @RequestBody ZoneDto dto
    ) {
        return zoneService.update(portId, zoneId, dto);
    }

    @DeleteMapping("/{zoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZone(
            @PathVariable Long portId,
            @PathVariable Long zoneId
    ) {
        zoneService.delete(portId, zoneId);
    }
}

