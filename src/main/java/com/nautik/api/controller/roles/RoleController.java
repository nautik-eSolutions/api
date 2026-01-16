package com.nautik.api.controller.roles;

import com.nautik.api.dto.roles.CapabilityDto;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles/")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/{companyId}/")
    public String createRole(
            @PathVariable int companyId,
            @RequestBody RoleCreateDto roleCreateDto
            ){
        System.out.println(roleCreateDto.getCapabilities());


        return "Ok";
    }

    @PostMapping("/{companyId}/capability")
    public ResponseEntity<Void> createCapability(
            @PathVariable int companyId,
            @RequestBody CapabilityDto capabilityDto
            ){

        return ResponseEntity.ok().build();
    }


}
