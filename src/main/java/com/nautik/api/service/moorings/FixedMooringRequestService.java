package com.nautik.api.service.moorings;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.exceptions.ForbiddenException;
import com.nautik.api.domain.moorings.FixedMooringRequest;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.mooring.create.CreateFixedMooringRequestDto;
import com.nautik.api.dto.mooring.FixedMooringRequestDto;
import com.nautik.api.dto.mooring.ReviewFixedMooringRequestDto;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.user.UserRepository;
import com.nautik.api.repository.moorings.FixedMooringRequestRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FixedMooringRequestService {

    private final FixedMooringRequestRepository requestRepository;
    private final PortRepository portRepository;
    private final MooringRepository mooringRepository;
    private final UserRepository userRepository;


    public List<FixedMooringRequestDto> getAllRequestsByPort(Integer portId) {
        List<FixedMooringRequest> requests = requestRepository.findByPortIdOrderByCreatedAtDesc(portId);
        return requests.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<FixedMooringRequestDto> getPendingRequestsByPort(Integer portId) {
        List<FixedMooringRequest> requests = requestRepository.findByPortIdAndStatusOrderByCreatedAtDesc(
                portId,
                FixedMooringRequest.RequestStatus.PENDING
        );
        return requests.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public FixedMooringRequestDto getRequestById(Integer requestId, Integer portId) {
        FixedMooringRequest request = requestRepository.findByIdAndPortId(requestId, portId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
        return mapToDto(request);
    }

    public FixedMooringRequestDto createRequest(CreateFixedMooringRequestDto dto, Integer userId) {
        Port port = portRepository.findById(dto.getPortId())
                .orElseThrow(() -> new EntityNotFoundException("Port not found"));

        Mooring mooring = null;
        if (dto.getMooringNumber() != null) {

        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FixedMooringRequest request = new FixedMooringRequest();
        request.setPort(port);
        request.setMooringNumber(dto.getMooringNumber());
        request.setUser(user);
        request.setMessage(dto.getMessage());
        request.setStatus(FixedMooringRequest.RequestStatus.PENDING);

        request = requestRepository.save(request);
        return mapToDto(request);
    }
    public FixedMooringRequestDto reviewRequest(
            Integer requestId,
            ReviewFixedMooringRequestDto dto,
            Integer portId
    ) {
        FixedMooringRequest request = requestRepository.findByIdAndPortId(requestId, portId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (!request.getStatus().equals(FixedMooringRequest.RequestStatus.PENDING)) {
            throw new EntityNotFoundException("Request was reviewed");
        }

        if ("APPROVE".equalsIgnoreCase(dto.getAction())) {
            request.setStatus(FixedMooringRequest.RequestStatus.APPROVED);
        } else if ("REJECT".equalsIgnoreCase(dto.getAction())) {
            request.setStatus(FixedMooringRequest.RequestStatus.REJECTED);
            request.setRejectionReason(dto.getRejectionReason());
        } else {
            throw new RuntimeException("Invalid action. use 'APPROVE' or 'REJECT'");
        }

        request = requestRepository.save(request);
        return mapToDto(request);
    }

    public void cancelRequest(Integer requestId, Integer userId) {
        FixedMooringRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        if (!request.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You don't have acces to this resource");
        }

        if (!request.getStatus().equals(FixedMooringRequest.RequestStatus.PENDING)) {
            throw new RuntimeException("You can cancel only pending requests");
        }

        request.setStatus(FixedMooringRequest.RequestStatus.CANCELLED);
        requestRepository.save(request);
    }

    public List<FixedMooringRequestDto> getRequestsByUser(Integer userId) {
        List<FixedMooringRequest> requests = requestRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return requests.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private FixedMooringRequestDto mapToDto(FixedMooringRequest request) {
        FixedMooringRequestDto dto = new FixedMooringRequestDto();
        dto.setId(request.getId());
        dto.setPortId(request.getPort().getId());
        dto.setPortName(request.getPort().getName());

        if (request.getMooringNumber() != null) {
            dto.setMooringNumber(request.getMooringNumber());
        }

        dto.setUserId(request.getUser().getId());
        dto.setUserFirstName(request.getUser().getFirstName());
        dto.setUserLastName(request.getUser().getLastName());
        dto.setUserEmail(request.getUser().getEmail());
        dto.setUserIdentificationDocument(request.getUser().getIdentificationDocument());

        dto.setMessage(request.getMessage());
        dto.setStatus(request.getStatus().name());
        dto.setRejectionReason(request.getRejectionReason());

        if (request.getCreatedAt() != null) {
            dto.setCreatedAt(String.valueOf(request.getCreatedAt()));
        }

        return dto;
    }
}