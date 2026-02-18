package com.nautik.api.controller.users;

import com.nautik.api.dto.user.UserAdminDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.service.jwt.JwtService;
import com.nautik.api.service.users.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/workers")
public class WorkersController {

    private final WorkerService workerService;
    private final JwtService  jwtService;


    @PreAuthorize("hasAuthority('ADMIN_COMPANY')")
    @GetMapping("/ports/{portId}")
    public ResponseEntity<List<UserDtoResponse>> getAllWorkersByPort(@PathVariable Integer portId,  @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader ){

        workerService.authorizeAdminToPortResource(userIdFromHeader(authHeader),portId);

        return ResponseEntity.ok(workerService.getWorkersByPort(portId));
    }


    @PostMapping("/company/{companyId}/administrator")
    public ResponseEntity<UserDtoResponse> createAdministrator(@RequestBody UserAdminDto adminDto, @PathVariable Integer companyId){
        return ResponseEntity.ok(workerService.createCompanyAdministrator(adminDto, companyId));
    }

    @PostMapping("/ports/{portsId}")
    public ResponseEntity<UserDtoResponse> createWorker(@RequestBody UserAdminDto adminDto, @PathVariable Integer portsId){
        return ResponseEntity.ok(workerService.createCompanyAdministrator(adminDto, portsId));
    }


    private Integer userIdFromHeader(String authHeader){
        String token = authHeader.substring(7);
        return Integer.parseInt(jwtService.extractUserId(token));
    }



}
