package com.nautik.api.controller.moorings;


import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.MooringIncidentDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.dto.mooring.create.MooringDimensionCreateDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.service.moorings.MooringService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moorings")
@RequiredArgsConstructor

public class MooringController {

    public final MooringService mooringService;

    @OnlyPortAdministrators
    @GetMapping
    public ResponseEntity<List<MooringDto>> getAllMoorings() {
        List<MooringDto> moorings = mooringService.findAll();
        return ResponseEntity.ok(moorings);
    }

    @OnlyPortAdministrators
    @GetMapping("/category/{mooringCategoriesId}")
    public ResponseEntity<List<MooringDto>> getAllMooringByCategoryId(
            @PathVariable(name = "mooringCategoriesId") Integer mooringCategoriesId){
        List<MooringDto> moorings = mooringService.findByMooringCategoryId(mooringCategoriesId);
        return ResponseEntity.ok(moorings);
    }

    @OnlyPortAdministrators
    @GetMapping("/{mooringId}")
    public ResponseEntity<MooringDto> getMooringById(
            @PathVariable(name="mooringId") Integer mooringId) {
        MooringDto mooring = mooringService.findById(mooringId);
        return ResponseEntity.ok(mooring);
    }


    @OnlyPortAdministrators
    @GetMapping("/ports/{portId}")
    public ResponseEntity<List<MooringDto>> getAllMooringsByPort(
            @PathVariable Integer portId) {

        List<MooringDto> moorings = mooringService.findAllByPortId(portId);

        return ResponseEntity.ok(moorings);
    }

    @OnlyPortAdministrators
    @GetMapping("/ports/{portId}/dimensions")
    public ResponseEntity<List<MooringDimensionDto>> getAllDimensionsByPort(
            @PathVariable Integer portId
    ) {
        List<MooringDimensionDto> dimensions = mooringService.getAllMooringsDimensions();
        return ResponseEntity.ok(dimensions);
    }



    @OnlyPortAdministrators
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<MooringDto> createMooring(@PathVariable Integer categoryId, @RequestBody CreateMooringDto mooring) {
        MooringDto dto = mooringService.createMooring(categoryId, mooring);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);


    }

    @OnlyPortAdministrators
    @DeleteMapping("/{mooringId}")
    public ResponseEntity<MooringDto> deleteMooringById(@PathVariable Integer mooringId) {
        mooringService.delete(mooringId);
        return ResponseEntity.ok().build();

    }

    @OnlyPortAdministrators
    @PutMapping("/{mooringId}")
    public ResponseEntity<MooringDto> updateMooring(@RequestBody CreateMooringDto mooring, @PathVariable Integer mooringId) {

        MooringDto mooringDtp = mooringService.update(mooringId, mooring);
        return ResponseEntity.ok(mooringDtp);

    }


    @OnlyPortAdministrators
    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<MooringDto>> getMooringByZoneId(@PathVariable Long zoneId) {
        List<MooringDto> moorings = mooringService.findAllByZoneId(Math.toIntExact(zoneId));
        return ResponseEntity.ok(moorings);
    }

    @OnlyPortAdministrators
    @PostMapping("/{mooringId}/incidents")
    public ResponseEntity<MooringIncidentDto> createMooringIncident(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails,
            @PathVariable Integer mooringId,
            @RequestBody MooringIncidentDto dto) {

        Integer portId = customAdminUserDetails.getPortId();
        MooringIncidentDto created = mooringService.createMooringIncident(portId, dto,mooringId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }
    @OnlyPortAdministrators
    @PutMapping("/incidents/{incidentId}")
    public ResponseEntity<MooringIncidentDto> updateMooringIncident(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails,
            @PathVariable Integer incidentId,
            @RequestBody MooringIncidentDto dto
    ){
        Integer portId = customAdminUserDetails.getPortId();
        MooringIncidentDto created = mooringService.updateMooringIncident(portId, dto,incidentId);
        return ResponseEntity.ok(created);
    }

    @OnlyPortAdministrators
    @GetMapping("/incidents/now")
    public ResponseEntity<List<MooringIncidentDto>> getMooringIncidentsByDate(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails
            ) {
        Integer portId = customAdminUserDetails.getPortId();
        List<MooringIncidentDto> incidents = mooringService.getCurrentMooringIncidents(portId);
        return ResponseEntity.ok(incidents);
    }

    @OnlyPortAdministrators
    @GetMapping("/incidents")
    public ResponseEntity<List<MooringIncidentDto>> getAllMooringIncidents(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails
    ) {
        Integer portId = customAdminUserDetails.getPortId();
        List<MooringIncidentDto> incidents = mooringService.getAllMooringIncidents(portId);
        return ResponseEntity.ok(incidents);
    }








}

