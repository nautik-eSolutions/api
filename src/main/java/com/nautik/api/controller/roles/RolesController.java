package com.nautik.api.controller.roles;

import com.nautik.api.dto.roles.CapabilityDto;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/configurations/")
public class RolesController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/{companyName}/{portName}/")
    public ResponseEntity<Void> createConfiguration(
            @PathVariable String companyName,
            @PathVariable String portName,
            @RequestBody RolesConfigurationDto rolesConfigurationDto

    ) {
        return ResponseEntity.ok().build();
    }


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


    @PostMapping("/{companyName}/{portName}/{configurationId}/capability/")
    public ResponseEntity<Void> createCapability(
            @PathVariable String companyName,
            @PathVariable String portName,
            @PathVariable int configurationId,
            @RequestBody CapabilityDto capabilityDto
    ) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{companyName}/{portName}/{configurationId}/roles/{roleName}/{capabilityName}")
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


}
