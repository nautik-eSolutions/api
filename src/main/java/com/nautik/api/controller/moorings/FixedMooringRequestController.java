package com.nautik.api.controller.moorings;

import com.nautik.api.configuration.PreAuthorizeConfig.OnlyPortAdministrators;
import com.nautik.api.dto.mooring.create.CreateFixedMooringRequestDto;
import com.nautik.api.dto.mooring.FixedMooringRequestDto;
import com.nautik.api.dto.mooring.ReviewFixedMooringRequestDto;
import com.nautik.api.service.moorings.FixedMooringRequestService;
import com.nautik.api.service.userDetails.CustomAdminUserDetails;
import com.nautik.api.dto.mooring.FixedMooringRequesCustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fixed-mooring-requests")
@RequiredArgsConstructor
public class FixedMooringRequestController {

    private final FixedMooringRequestService fixedMooringRequestService;

    @GetMapping
    @OnlyPortAdministrators
    public ResponseEntity<List<FixedMooringRequestDto>> getAllRequests(Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        List<FixedMooringRequestDto> requests = fixedMooringRequestService.getAllRequestsByPort(portId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pending")
    @OnlyPortAdministrators
    public ResponseEntity<List<FixedMooringRequestDto>> getPendingRequests(Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        List<FixedMooringRequestDto> requests = fixedMooringRequestService.getPendingRequestsByPort(portId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{requestId}")
    @OnlyPortAdministrators
    public ResponseEntity<FixedMooringRequestDto> getRequestById(
            @PathVariable Integer requestId,
            Authentication authentication) {
        CustomAdminUserDetails userDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = userDetails.getPortId();

        FixedMooringRequestDto request = fixedMooringRequestService.getRequestById(requestId, portId);
        return ResponseEntity.ok(request);
    }

    @PostMapping
    public ResponseEntity<FixedMooringRequestDto> createRequest(
            @RequestBody CreateFixedMooringRequestDto dto,
            Authentication authentication) {
        Integer userId = Integer.parseInt(((User) authentication).getUsername());

        FixedMooringRequestDto created = fixedMooringRequestService.createRequest(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{requestId}/review")
    @OnlyPortAdministrators
    public ResponseEntity<FixedMooringRequestDto> reviewRequest(
            @PathVariable Integer requestId,
            @RequestBody ReviewFixedMooringRequestDto dto,
            Authentication authentication) {
        CustomAdminUserDetails adminDetails = (CustomAdminUserDetails) authentication.getPrincipal();
        Integer portId = adminDetails.getPortId();

        FixedMooringRequestDto reviewed = fixedMooringRequestService.reviewRequest(
                requestId,
                dto,
                portId);

        return ResponseEntity.ok(reviewed);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> cancelRequest(
            @PathVariable Integer requestId,
            Authentication authentication) {
        Integer userId = Integer.parseInt(((User) authentication.getPrincipal()).getUsername());
        fixedMooringRequestService.cancelRequest(requestId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FixedMooringRequesCustomerDto>> getMyRequests(Authentication authentication) {
        Integer userId = Integer.parseInt(((User) authentication.getPrincipal()).getUsername());
        List<FixedMooringRequesCustomerDto> requests = fixedMooringRequestService.getRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }

}
