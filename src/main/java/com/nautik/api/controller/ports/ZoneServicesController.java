package com.nautik.api.controller.ports;

import com.nautik.api.dto.service.CreateServiceDto;
import com.nautik.api.dto.service.ServiceDto;
import com.nautik.api.service.port.ServicesOffered;
import com.nautik.api.service.port.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ZoneServicesController {

    private final ServicesOffered servicesOffered;
    private final ZoneService zoneService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_COMPANY', 'ADMIN_PORT')")
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(servicesOffered.findAll());
    }

    @GetMapping("/zone/{zoneId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_COMPANY', 'ADMIN_PORT')")
    public ResponseEntity<List<ServiceDto>> getServicesByZone(@PathVariable Integer zoneId) {
        return ResponseEntity.ok(zoneService.getServicesByZone(zoneId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<ServiceDto> createService(@RequestBody CreateServiceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicesOffered.create(dto));
    }

    @PostMapping("/{serviceId}/zone/{zoneId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_COMPANY', 'ADMIN_PORT')")
    public ResponseEntity<Void> addToZone(
            @PathVariable Integer serviceId,
            @PathVariable Integer zoneId
    ) {
        zoneService.addServiceToZone(zoneId, serviceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{serviceId}/zone/{zoneId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_COMPANY', 'ADMIN_PORT')")
    public ResponseEntity<Void> removeFromZone(
            @PathVariable Integer serviceId,
            @PathVariable Integer zoneId
    ) {
        zoneService.removeServiceFromZone(zoneId, serviceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId) {
        servicesOffered.delete(serviceId);
        return ResponseEntity.ok().build();
    }
}