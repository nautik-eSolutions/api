package com.nautik.api.controller.ports;

import com.nautik.api.configuration.preAuthorizeConfig.OnlyCompanyAdministrators;
import com.nautik.api.configuration.preAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.port.PortDto;
import com.nautik.api.dto.port.create.CreatePortDto;
import com.nautik.api.service.port.PortService;
import com.nautik.api.service.port.S3PortImageService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.nautik.api.dto.port.PortImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ports")
@RequiredArgsConstructor
public class PortController {
    private final S3PortImageService s3PortImageService;
    private final PortService portService;

    @GetMapping
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<List<PortDto>> getAllPorts() {
        List<PortDto> allPorts = portService.findAll();
        return ResponseEntity.ok(allPorts);
    }

    @GetMapping("/company/administrator")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<List<PortDto>> getAllPortsByCompanyAdmin(
            @AuthenticationPrincipal UserDetails userDetails) {
        Integer userId = Integer.parseInt(userDetails.getUsername());
        List<PortDto> allPorts = portService.findAllByCompanyAdmin(userId);
        return ResponseEntity.ok(allPorts);
    }

    @GetMapping("/port-administrator")
    @PreAuthorize("hasAuthority('ADMIN_PORT')")
    public ResponseEntity<PortDto> getPortByPortAdmin(
            Authentication authentication
    ) {
        CustomAdminUserDetails adminUserDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer userId = Integer.parseInt(adminUserDetails.getUsername());
        PortDto portDto = portService.findAllByPortAdmin(userId);
        return ResponseEntity.ok(portDto);
    }

    @GetMapping("/{portId}")
    @OnlyPortAdministrators
    public ResponseEntity<PortDto> getPortById(@PathVariable Integer portId) {
        PortDto port = portService.findById(portId);
        return ResponseEntity.ok(port);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<PortDto> createPort(
            @RequestBody CreatePortDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Integer userId = Integer.parseInt(userDetails.getUsername());
        PortDto portCreated = portService.create(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(portCreated);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<PortDto> updatePort(
            @PathVariable Integer id,
            @RequestBody CreatePortDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Integer userId = Integer.parseInt(userDetails.getUsername());
        PortDto updatePort = portService.update(id, dto, userId);
        return ResponseEntity.ok(updatePort);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<Void> deletePort(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Integer userId = Integer.parseInt(userDetails.getUsername());
        portService.delete(id, userId);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/{portId}/images")
    @OnlyCompanyAdministrators
    public ResponseEntity<List<PortImageDto>> getPortImages(@PathVariable Integer portId) {
        return ResponseEntity.ok(s3PortImageService.getPortImages(portId));
    }

    @PostMapping(value = "/{portId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @OnlyCompanyAdministrators
    public ResponseEntity<PortImageDto> uploadPortImage(
            @PathVariable Integer portId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s3PortImageService.uploadPortImage(portId, file));
    }

    @GetMapping("/images")
    @OnlyPortAdministrators
    public ResponseEntity<List<PortImageDto>> getPortImagesByPortAdmin(
            Authentication authentication
           ) {
        CustomAdminUserDetails adminUserDetails = (CustomAdminUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(s3PortImageService.getPortImages(adminUserDetails.getPortId()));
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @OnlyPortAdministrators
    public ResponseEntity<PortImageDto> uploadPortImageByPortAdmin(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) {
        CustomAdminUserDetails adminUserDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(s3PortImageService.uploadPortImage(adminUserDetails.getPortId(), file));
    }


}