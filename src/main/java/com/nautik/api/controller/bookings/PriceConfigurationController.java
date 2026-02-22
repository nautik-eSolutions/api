package com.nautik.api.controller.bookings;

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
    public ResponseEntity<List<PriceConfigurationDto>> getAll(@PathVariable Integer portId) {
        return ResponseEntity.ok(priceConfigurationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceConfigurationDto> getById(
            @PathVariable Integer portId,
            @PathVariable Integer id) {
        return ResponseEntity.ok(priceConfigurationService.getById( id));
    }

    @PostMapping
    public ResponseEntity<PriceConfigurationDto> create(
            @PathVariable Integer portId,
            @RequestBody PriceConfigurationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(priceConfigurationService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceConfigurationDto> update(
            @PathVariable Integer portId,
            @PathVariable Integer id,
            @RequestBody PriceConfigurationDto dto) {
        return ResponseEntity.ok(priceConfigurationService.update( id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer portId,
            @PathVariable Integer id) {
        priceConfigurationService.delete( id);
        return ResponseEntity.noContent().build();
    }






}
