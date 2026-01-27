package com.nautik.api.controller.roles;

import com.nautik.api.dto.roles.CapabilityDto;
import com.nautik.api.dto.roles.RoleResponseDto;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.service.roles.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/users/configurations/")
public class RolesController {


    private final RolesService roleService;

    //RolesConfigurationRepository
    @PostMapping("/{companyName}")
    public ResponseEntity<RolesConfigurationDto> createRolesConfiguration(
            @PathVariable String companyName,
            @RequestBody RolesConfigurationDto rolesConfigurationDto

    ) {

        RolesConfigurationDto createdRoleConfiguration = roleService.createRolesConfiguration(companyName,rolesConfigurationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoleConfiguration);
    }

    @DeleteMapping("/{companyName}/{configurationName}")
    public ResponseEntity<Void> deleteConfiguration(
            @PathVariable String companyName,
            @PathVariable String configurationName
    ) {

        roleService.deleteRolesConfiguration(companyName,configurationName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<List<RolesConfigurationDto>> getAllRolesConfiguration(
            @PathVariable String companyName
    ) {
        List<RolesConfigurationDto> rolesConfigurations = roleService.getAllCompanyConfigurations(companyName);
        return ResponseEntity.ok(rolesConfigurations);
    }

    //Roles
    @PostMapping("/{companyName}/{configurationName}/roles")
    public ResponseEntity<RoleResponseDto> createRole(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @RequestBody RoleCreateDto roleCreateDto
    ) {
    RoleResponseDto roleResponseDto = roleService.createRole(companyName, configurationName, roleCreateDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(roleResponseDto);

    }

    @GetMapping("/{companyName}/{configurationName}/roles")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(
            @PathVariable String companyName,
            @PathVariable String configurationName
    ) {

        List<RoleResponseDto>roles = roleService.getAllRolesByConfigurationName(companyName, configurationName);
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{companyName}/{configurationName}/roles/{roleName}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable String configurationName,
            @PathVariable String companyName,
            @PathVariable String roleName) {

        roleService.deleteRole(configurationName,companyName,roleName);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{companyName}/{configurationName}/roles")
    public ResponseEntity<RoleResponseDto> updateRole(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @RequestBody RoleCreateDto roleCreateDto
    ) {

        RoleResponseDto role =  roleService.updateRole(companyName,configurationName,roleCreateDto);

        return ResponseEntity.ok(role);
    }


    // Capabilities

    @PostMapping("/{companyName}/{configurationName}/capabilities")
    public ResponseEntity<CapabilityDto> createCapability(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @RequestBody CapabilityDto capabilityDto
    ) {

        CapabilityDto capability = roleService.createCapability(companyName,configurationName,capabilityDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(capability);

    }

    @PutMapping("/{companyName}/{configurationName}/capabilities")
    public ResponseEntity<CapabilityDto> updateCapability(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @RequestBody CapabilityDto capabilityDto
    ) {
        CapabilityDto capability = roleService.updateCapability(companyName,configurationName,capabilityDto);

        return ResponseEntity.ok(capability);
    }

    @GetMapping("/{companyName}/{configurationName}/capabilities")
    public ResponseEntity<List<CapabilityDto>> getAllCapabilities(
            @PathVariable String companyName,
            @PathVariable String configurationName
    ) {
        List<CapabilityDto> capabilities =  roleService.getAllCapabilities(companyName, configurationName);
        return ResponseEntity.ok(capabilities);
    }


    @GetMapping("/{companyName}/{configurationName}/capabilities/{capabilityName}")
    public ResponseEntity<CapabilityDto> getCapability(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @PathVariable String capabilityName
    ) {
        CapabilityDto capability = roleService.getCapability(companyName, configurationName, capabilityName);

        return ResponseEntity.ok(capability);
    }


    @PostMapping("/{companyName}/{configurationName}/roles/{roleName}/capability/{capabilityName}")
    public ResponseEntity<List<CapabilityDto>> assignCapabilityRole(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @PathVariable String roleName,
            @PathVariable String capabilityName
    ) {
        List<CapabilityDto> capabilities = roleService.assignCapabilityToRole(companyName, configurationName, roleName, capabilityName);

        return ResponseEntity.ok(capabilities);
    }

    @DeleteMapping("/{companyName}/{configurationName}/roles/{roleName}/capability/{capabilityName}")
    public ResponseEntity<List<CapabilityDto>> removeCapabilityRole(
            @PathVariable String companyName,
            @PathVariable String configurationName,
            @PathVariable String roleName,
            @PathVariable String capabilityName
    ) {
        List<CapabilityDto> capabilites =  roleService.removeCapabilityToRole(companyName, configurationName, roleName, capabilityName);
        return ResponseEntity.ok(capabilites);
    }






}
