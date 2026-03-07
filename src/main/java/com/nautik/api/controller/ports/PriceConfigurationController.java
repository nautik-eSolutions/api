package com.nautik.api.controller.ports;

import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.service.bookings.PriceConfigurationService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-configurations")
@RequiredArgsConstructor
public class PriceConfigurationController {


    private final PriceConfigurationService priceConfigurationService;


    @OnlyPortAdministrators
    @GetMapping
    public ResponseEntity<List<PriceConfigurationDto>> getAllByPortId(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails) {
        Integer portId = customAdminUserDetails.getAdminId();
        return ResponseEntity.ok(priceConfigurationService.getAllByPortId(portId));
    }

    @OnlyPortAdministrators
    @PostMapping
    public ResponseEntity<PriceConfigurationDto> createPriceConfiguration(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails,
            @RequestBody PriceConfigurationDto dto) {
        Integer portId = customAdminUserDetails.getAdminId();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(priceConfigurationService.createPriceConfiguration(portId, dto));
    }

    @OnlyPortAdministrators
    @PutMapping("/{id}")
    public ResponseEntity<PriceConfigurationDto> updatePriceConfiguration(
            @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails,
            @PathVariable Integer id,
            @RequestBody PriceConfigurationDto dto) {
        Integer portId = customAdminUserDetails.getAdminId();

        return ResponseEntity.ok(priceConfigurationService.updatePriceConfiguration( portId,id, dto));
    }

    @OnlyPortAdministrators
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceConfiguration(
            @PathVariable Integer portId,
            @PathVariable Integer id) {
        priceConfigurationService.deletePriceConfiguration(portId, id);
        return ResponseEntity.noContent().build();
    }






}
