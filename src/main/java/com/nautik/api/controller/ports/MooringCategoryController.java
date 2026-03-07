package com.nautik.api.controller.ports;

import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringCategoryInfoDto;
import com.nautik.api.service.moorings.MooringCategoryService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/mooring-categories")
@RequiredArgsConstructor
public class MooringCategoryController {

    private final MooringCategoryService mooringCategoryService;

    @OnlyPortAdministrators
    @GetMapping
    public ResponseEntity<List<MooringCategoryInfoDto>> getAllMooringCategoriesByPortId(@AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails) {
        Integer portId = customAdminUserDetails.getPortId();
        return ResponseEntity.ok(mooringCategoryService.getAllByPortId(portId));
    }

    @OnlyPortAdministrators
    @GetMapping("/{id}")
    public ResponseEntity<MooringCategoryDto> getMooringCategoryById(@AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails, @PathVariable Integer id) {
        Integer portId = customAdminUserDetails.getPortId();
        return ResponseEntity.ok(mooringCategoryService.getById(portId, id));
    }


    @OnlyPortAdministrators
    @PostMapping
    public ResponseEntity<MooringCategoryInfoDto> createMooringCategory(@AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails, @RequestBody MooringCategoryInfoDto dto) {
        Integer portId = customAdminUserDetails.getPortId();
        return ResponseEntity.status(HttpStatus.CREATED).body(mooringCategoryService.createMooringCategory(portId, dto));
    }

    @OnlyPortAdministrators
    @PutMapping("/{id}")
    public ResponseEntity<MooringCategoryInfoDto> updateMooringCategory(@AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails, @PathVariable Integer id, @RequestBody MooringCategoryInfoDto dto) {
        Integer portId = customAdminUserDetails.getPortId();
        return ResponseEntity.ok(mooringCategoryService.updateMooringCategory(portId, id, dto));
    }


    @OnlyPortAdministrators
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMooringCategory(@PathVariable Integer id, @AuthenticationPrincipal CustomAdminUserDetails customAdminUserDetails) {

        Integer portId = customAdminUserDetails.getPortId();
        mooringCategoryService.deleteMooringCategory(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{mooringCategoryId}/price-configurations/{priceConfigurationId}")
    public ResponseEntity<MooringCategoryDto> assignPriceConfigurationToMooringCategory(
            @PathVariable Integer mooringCategoryId,
            @PathVariable Integer priceConfigurationId,
            Authentication authentication
    ){
        Integer portId = ((CustomAdminUserDetails) authentication.getPrincipal()).getPortId();

       MooringCategoryDto mooringCategoryDto =  mooringCategoryService.assingPriceConfigurationToMooringCategory(portId,priceConfigurationId,mooringCategoryId);

       return new ResponseEntity<>(mooringCategoryDto,HttpStatus.OK);
    }


    @DeleteMapping("/{mooringCategoryId}/price-configurations/{priceConfigurationId}")
    public ResponseEntity<MooringCategoryDto> deAssingPriceConfigurationToMooringCategory(
            @PathVariable Integer mooringCategoryId,
            @PathVariable Integer priceConfigurationId,
                   Authentication authentication
    ){
        Integer portId = (( CustomAdminUserDetails) authentication.getPrincipal()).getPortId();

        MooringCategoryDto mooringCategoryDto =  mooringCategoryService.deAssignPriceConfigurationFromMooringCategory(portId,priceConfigurationId,mooringCategoryId);

        return new ResponseEntity<>(mooringCategoryDto,HttpStatus.OK);

    }




}