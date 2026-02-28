package com.nautik.api.controller.users;

import com.nautik.api.dto.admin.AdminPortRequest;
import com.nautik.api.dto.admin.AdminResponse;
import com.nautik.api.dto.admin.AdminCompanyRequest;
import com.nautik.api.service.admin.AdminService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<AdminResponse> updateCompanyAdmin(
            @PathVariable Integer adminId,
           @Valid @RequestBody AdminCompanyRequest request) {
        AdminResponse response = adminService.updateCompanyAdmin(adminId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/company/{adminId}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<Void> deleteCompanyAdmin(@PathVariable Integer adminId) {
        adminService.deleteCompanyAdmin(adminId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ports/{portId}")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<AdminResponse> createPortAdmin(
            @PathVariable Integer portId,
            @RequestBody AdminPortRequest request,
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer adminCompanyId = userDetails.getAdminId();
        AdminResponse response = adminService.createPortAdmin(adminCompanyId, portId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/company/ports")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<List<AdminResponse>> getPortAdmins(
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer adminCompanyId = userDetails.getAdminId();
        List<AdminResponse> admins = adminService.getPortAdmins(adminCompanyId);
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/ports/{portId}/{adminId}")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<AdminResponse> updatePortAdmin(
            @PathVariable Integer portId,
            @PathVariable Integer adminId,
            @RequestBody AdminPortRequest request,
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer adminCompanyId = userDetails.getAdminId();
        Integer companyId = userDetails.getCompanyId();

        AdminResponse response = adminService.updatePortAdmin(companyId, portId, adminId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ports/{portId}/{adminId}")
    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    public ResponseEntity<Void> deletePortAdmin(
            @PathVariable Integer portId,
            @PathVariable Integer adminId,
            Authentication authentication) {

        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer companyId = userDetails.getCompanyId();
        adminService.deletePortAdmin( companyId, portId, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdmin(@PathVariable Integer adminId) {
        AdminResponse response = adminService.getAdmin(adminId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        List<AdminResponse> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }
}