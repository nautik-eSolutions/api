package com.nautik.api.service.port;

import com.nautik.api.domain.ZoneServicesOffered;
import com.nautik.api.domain.ZoneServicesOffered;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.dto.service.CreateServiceDto;
import com.nautik.api.dto.service.ServiceDto;
import com.nautik.api.repository.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServicesOffered {

    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    public List<ServiceDto> findAll() {
        return serviceRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, ServiceDto.class))
                .toList();
    }

    public ServiceDto create(CreateServiceDto dto) {
        ZoneServicesOffered service = modelMapper.map(dto, ZoneServicesOffered.class);
        return modelMapper.map(serviceRepository.save(service), ServiceDto.class);
    }

    public void delete(Integer serviceId) {
        ZoneServicesOffered service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        serviceRepository.delete(service);
    }
}