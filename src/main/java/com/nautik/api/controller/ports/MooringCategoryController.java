package com.nautik.api.controller.ports;

import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringCategoryInfoDto;
import com.nautik.api.service.moorings.MooringCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mooring-categories/ports/{portId}")
@RequiredArgsConstructor
public class MooringCategoryController {

    private final MooringCategoryService mooringCategoryService;

    @GetMapping
    public ResponseEntity<List<MooringCategoryInfoDto>> getAllByPortId(@PathVariable Integer portId) {
        return ResponseEntity.ok(mooringCategoryService.getAllByPortId(portId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MooringCategoryInfoDto> getById(
            @PathVariable Integer portId,
            @PathVariable Integer id) {
        return ResponseEntity.ok(mooringCategoryService.getById(portId, id));
    }

    @PostMapping
    public ResponseEntity<MooringCategoryInfoDto> createMooringCategory(
            @PathVariable Integer portId,
            @RequestBody MooringCategoryInfoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mooringCategoryService.createMooringCategory(portId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MooringCategoryInfoDto> updateMooringCategory(
            @PathVariable Integer portId,
            @PathVariable Integer id,
            @RequestBody MooringCategoryInfoDto dto) {
        return ResponseEntity.ok(mooringCategoryService.updateMooringCategory(portId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMooringCategory(
            @PathVariable Integer id) {
        mooringCategoryService.deleteMooringCategory(id);
        return ResponseEntity.noContent().build();
    }
}