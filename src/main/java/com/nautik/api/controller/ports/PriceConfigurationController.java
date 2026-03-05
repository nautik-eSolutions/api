package com.nautik.api.controller.ports;

import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.service.bookings.PriceConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-configurations/ports/{portId}")
@RequiredArgsConstructor
public class PriceConfigurationController {


    private final PriceConfigurationService priceConfigurationService;


    @GetMapping
    public ResponseEntity<List<PriceConfigurationDto>> getAllByPortId(@PathVariable(name = "portId") Integer portId) {
        return ResponseEntity.ok(priceConfigurationService.getAllByPortId(portId));
    }


    @PostMapping
    public ResponseEntity<PriceConfigurationDto> createPriceConfiguration(
            @PathVariable Integer portId,
            @RequestBody PriceConfigurationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(priceConfigurationService.createPriceConfiguration(portId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceConfigurationDto> updatePriceConfiguration(
            @PathVariable Integer id,
            @RequestBody PriceConfigurationDto dto) {
        return ResponseEntity.ok(priceConfigurationService.updatePriceConfiguration( id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceConfiguration(
            @PathVariable Integer portId,
            @PathVariable Integer id) {
        priceConfigurationService.deletePriceConfiguration( id);
        return ResponseEntity.noContent().build();
    }






}
