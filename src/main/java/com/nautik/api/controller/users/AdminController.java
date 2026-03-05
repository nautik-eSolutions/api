package com.nautik.api.controller.users;

import com.nautik.api.configuration.preAuthorizeConfig.OnlyCompanyAdministrators;
import com.nautik.api.configuration.preAuthorizeConfig.OnlyDevelopers;
import com.nautik.api.dto.admin.AdminPortRequest;
import com.nautik.api.dto.admin.AdminResponse;
import com.nautik.api.dto.admin.AdminCompanyRequest;
import com.nautik.api.service.admin.AdminService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrators")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/company")
    //@PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<AdminResponse> createCompanyAdmin(@Valid @RequestBody AdminCompanyRequest request) {
        AdminResponse response = adminService.createCompanyAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/company/{adminId}")
    @OnlyDevelopers
    public ResponseEntity<AdminResponse> updateCompanyAdmin(
            @PathVariable("adminId") Integer adminId,
           @Valid @RequestBody AdminCompanyRequest request) {
        AdminResponse response = adminService.updateCompanyAdmin(adminId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/company/{adminId}")
    @OnlyDevelopers
    public ResponseEntity<Void> deleteCompanyAdmin( @PathVariable("adminId") Integer adminId) {
        adminService.deleteCompanyAdmin(adminId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ports/{portId}")
    @OnlyCompanyAdministrators
    public ResponseEntity<AdminResponse> createPortAdmin(
            @PathVariable(name = "portId") Integer portId,
            @RequestBody AdminPortRequest request,
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer adminCompanyId = userDetails.getAdminId();
        AdminResponse response = adminService.createPortAdmin(adminCompanyId, portId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/company/ports")
    @OnlyCompanyAdministrators
    public ResponseEntity<List<AdminResponse>> getPortAdmins(
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer adminCompanyId = userDetails.getAdminId();
        List<AdminResponse> admins = adminService.getPortAdmins(adminCompanyId);
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/ports/{portId}/{adminId}")
    @OnlyCompanyAdministrators
    public ResponseEntity<AdminResponse> updatePortAdmin(
            @PathVariable(name = "portId") Integer portId,
            @PathVariable(name = "adminId") Integer adminId,
            @RequestBody AdminPortRequest request,
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer companyId = userDetails.getCompanyId();

        AdminResponse response = adminService.updatePortAdmin(companyId, portId, adminId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ports/{portId}/{adminId}")
    @OnlyCompanyAdministrators
    public ResponseEntity<Void> deletePortAdmin(
            @PathVariable("portId") Integer portId,
            @PathVariable("adminId") Integer adminId,
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer companyId = userDetails.getCompanyId();
        adminService.deletePortAdmin( companyId, portId, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdmin(
            @PathVariable("adminId") Integer adminId) {
        AdminResponse response = adminService.getAdmin(adminId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        List<AdminResponse> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }
}