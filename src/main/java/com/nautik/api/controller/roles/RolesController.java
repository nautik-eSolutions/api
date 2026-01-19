package com.nautik.api.controller.roles;

import com.nautik.api.dto.roles.CapabilityDto;
import com.nautik.api.dto.roles.RoleResponseDto;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/configurations/")
public class RolesController {

    @Autowired
    private RoleService roleService;

    //RolesConfiguration
    @PostMapping("/{companyName}/{portName}/")
    public ResponseEntity<Void> createConfiguration(
            @PathVariable String companyName,
            @PathVariable String portName,
            @RequestBody RolesConfigurationDto rolesConfigurationDto

    ) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{companyName}/{portName}/{configurationId}")
    public ResponseEntity<Void> deleteConfiguration(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{companyName}/{portName}/")
    public ResponseEntity<List<RolesConfigurationDto>> getAllRolesConfiguration(
            @PathVariable String companyName,
            @PathVariable String portName

    ) {
        return ResponseEntity.ok().build();
    }

    //Roles
    @PostMapping("/{companyName}/{portName}/{configurationId}/roles/")
    public String createRole(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId,
            @RequestBody RoleCreateDto roleCreateDto
    ) {
        System.out.println(roleCreateDto.getCapabilities());


        return "Ok";
    }

    @GetMapping("/{companyName}/{portName}/{configurationId}/roles/")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId
    ) {


        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{companyName}/{portName}/{configurationId}/roles/{roleName}")
    public ResponseEntity<RoleResponseDto> deleteRole(
            @PathVariable int configurationId,
            @PathVariable String portName,
            @PathVariable String companyName,
            @PathVariable String roleName) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{companyName}/{portName}/{configurationId}/roles/")
    public String updateRole(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId,
            @RequestBody RoleCreateDto roleCreateDto
    ) {
        System.out.println(roleCreateDto.getCapabilities());


        return "Ok";
    }

    //dd
    // Capabilities

    @PostMapping("/{companyName}/{portName}/capabilities/")
    public ResponseEntity<Void> createCapability(
            @PathVariable String companyName,
            @PathVariable String portName,
            @RequestBody CapabilityDto capabilityDto
    ) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{companyName}/{portName}/capabilities/")
    public ResponseEntity<Void> updateCapability(
            @PathVariable String companyName,
            @PathVariable String portName,
            @RequestBody CapabilityDto capabilityDto
    ) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{companyName}/{portName}/capabilities")
    public ResponseEntity<List<CapabilityDto>> getAllCapabilities(
            @PathVariable String companyName,
            @PathVariable String portName
    ) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{companyName}/{portName}/capabilities/{capabilityId}")
    public ResponseEntity<CapabilityDto> getCapability(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int capabilityId
    ) {
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{companyName}/{portName}/{configurationId}/roles/{roleName}/capability/{capabilityName}")
    public ResponseEntity<Void> assignCapabilityRole(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId,
            @PathVariable String roleName,
            @PathVariable String capabilityName,
            @RequestBody CapabilityDto capabilityDto
    ) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{companyName}/{portName}/{configurationId}/roles/{roleName}/capability/{capabilityName}")
    public ResponseEntity<Void> removeCapabilityRole(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId,
            @PathVariable String roleName,
            @PathVariable String capabilityName,
            @RequestBody CapabilityDto capabilityDto
    ) {

        return ResponseEntity.ok().build();
    }






}
