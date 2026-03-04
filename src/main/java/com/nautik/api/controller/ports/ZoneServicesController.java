package com.nautik.api.controller.ports;

import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.service.CreateServiceDto;
import com.nautik.api.dto.service.ServiceDto;
import com.nautik.api.service.port.ServicesOffered;
import com.nautik.api.service.port.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ZoneServicesController {

    private final ServicesOffered servicesOffered;
    private final ZoneService zoneService;

    @GetMapping
    @OnlyPortAdministrators
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(servicesOffered.findAll());
    }

    @GetMapping("/zone/{zoneId}")
    @OnlyPortAdministrators
    public ResponseEntity<List<ServiceDto>> getServicesByZone(@PathVariable(name = "zoneId") Integer zoneId) {
        return ResponseEntity.ok(zoneService.getServicesByZone(zoneId));
    }

    @PostMapping
    @OnlyPortAdministrators
    public ResponseEntity<ServiceDto> createService(@RequestBody CreateServiceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicesOffered.create(dto));
    }

    @PostMapping("/{serviceId}/zone/{zoneId}")
    @OnlyPortAdministrators
    public ResponseEntity<Void> addToZone(
            @PathVariable(name = "serviceId") Integer serviceId,
            @PathVariable(name = "zoneId") Integer zoneId
    ) {
        zoneService.addServiceToZone(zoneId, serviceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{serviceId}/zone/{zoneId}")
    @OnlyPortAdministrators
    public ResponseEntity<Void> removeFromZone(
            @PathVariable(name = "serviceId") Integer serviceId,
            @PathVariable(name = "zoneId" )Integer zoneId
    ) {
        zoneService.removeServiceFromZone(zoneId, serviceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{serviceId}")
    @OnlyPortAdministrators
    public ResponseEntity<Void> deleteService(@PathVariable(name = "serviceId") Integer serviceId) {
        servicesOffered.delete(serviceId);
        return ResponseEntity.ok().build();
    }
}