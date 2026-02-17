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


@RequestMapping("/api/v1/users/configurations/")
public class RolesController {


    private final RolesService roleService;

    /*
    //RolesConfigurationRepository
    @PostMapping("/{companyId}")
    public ResponseEntity<RolesConfigurationDto> createRolesConfiguration(
            @PathVariable Long companyId,
            @RequestBody RolesConfigurationDto rolesConfigurationDto

    ) {

        RolesConfigurationDto createdRoleConfiguration = roleService.createRolesConfiguration(companyId,rolesConfigurationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoleConfiguration);
    }

    @DeleteMapping("/{companyId}/{configurationId}")
    public ResponseEntity<Void> deleteConfiguration(
            @PathVariable Long companyId,
            @PathVariable Long configurationId
    ) {

        roleService.deleteRolesConfiguration(companyId, configurationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<List<RolesConfigurationDto>> getAllRolesConfiguration(
            @PathVariable Long companyId
    ) {
        List<RolesConfigurationDto> rolesConfigurations = roleService.getAllCompanyConfigurations(companyId);
        return ResponseEntity.ok(rolesConfigurations);
    }

    //Roles
    @PostMapping("/{companyId}/{configurationId}/roles")
    public ResponseEntity<RoleResponseDto> createRole(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @RequestBody RoleCreateDto roleCreateDto
    ) {
    RoleResponseDto roleResponseDto = roleService.createRole(companyId, configurationId, roleCreateDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(roleResponseDto);

    }

    @GetMapping("/{companyId}/{configurationId}/roles")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(
            @PathVariable Long companyId,
            @PathVariable Long configurationId
    ) {

        List<RoleResponseDto>roles = roleService.getAllRolesByConfigurationId(companyId, configurationId);
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{companyId}/{configurationId}/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Long configurationId,
            @PathVariable Long companyId,
            @PathVariable Long roleId) {

        roleService.deleteRole(configurationId, companyId, roleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{companyId}/{configurationId}/roles")
    public ResponseEntity<RoleResponseDto> updateRole(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @RequestBody RoleCreateDto roleCreateDto
    ) {

        RoleResponseDto role =  roleService.updateRole(companyId, configurationId,roleCreateDto);

        return ResponseEntity.ok(role);
    }


    // Capabilities

    @PostMapping("/{companyId}/{configurationId}/capabilities")
    public ResponseEntity<CapabilityDto> createCapability(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @RequestBody CapabilityDto capabilityDto
    ) {

        CapabilityDto capability = roleService.createCapability(companyId,configurationId,capabilityDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(capability);

    }

    @PutMapping("/{companyId}/{configurationId}/capabilities")
    public ResponseEntity<CapabilityDto> updateCapability(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @RequestBody CapabilityDto capabilityDto
    ) {
        CapabilityDto capability = roleService.updateCapability(companyId,configurationId,capabilityDto);

        return ResponseEntity.ok(capability);
    }

    @GetMapping("/{companyId}/{configurationId}/capabilities")
    public ResponseEntity<List<CapabilityDto>> getAllCapabilities(
            @PathVariable Long companyId,
            @PathVariable Long configurationId
    ) {
        List<CapabilityDto> capabilities =  roleService.getAllCapabilities(companyId, configurationId);
        return ResponseEntity.ok(capabilities);
    }


    @GetMapping("/{companyId}/{configurationId}/capabilities/{capabilityId}")
    public ResponseEntity<CapabilityDto> getCapability(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @PathVariable Long capabilityId
    ) {
        CapabilityDto capability = roleService.getCapability(companyId, configurationId, capabilityId);

        return ResponseEntity.ok(capability);
    }


    @PostMapping("/{companyId}/{configurationId}/roles/{roleId}/capability/{capabilityId}")
    public ResponseEntity<List<CapabilityDto>> assignCapabilityRole(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @PathVariable Long roleId,
            @PathVariable Long capabilityId
    ) {
        List<CapabilityDto> capabilities = roleService.assignCapabilityToRole(companyId, configurationId, roleId, capabilityId);

        return ResponseEntity.ok(capabilities);
    }

    @DeleteMapping("/{companyId}/{configurationId}/roles/{roleId}/capability/{capabilityId}")
    public ResponseEntity<List<CapabilityDto>> removeCapabilityRole(
            @PathVariable Long companyId,
            @PathVariable Long configurationId,
            @PathVariable Long roleId,
            @PathVariable Long capabilityId
    ) {
        List<CapabilityDto> capabilites =  roleService.removeCapabilityToRole(companyId, configurationId, roleId, capabilityId);
        return ResponseEntity.ok(capabilites);
    }

*/






}
