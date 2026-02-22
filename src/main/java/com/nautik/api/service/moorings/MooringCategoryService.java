package com.nautik.api.service.moorings;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.MooringDimension;
import com.nautik.api.domain.Zone;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.dto.mooring.MooringCategoryInfoDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MooringCategoryService {

    private final MooringCategoryRepository mooringCategoryRepository;
    private final MooringDimensionRepository mooringDimensionRepository;
    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;

    public List<MooringCategoryInfoDto> getAllByPortId(Integer portId) {
        return mooringCategoryRepository.findAllByZonePortId(portId)
                .stream()
                .map(mc -> modelMapper.map(mc, MooringCategoryInfoDto.class))
                .toList();
    }

    public MooringCategoryInfoDto getById(Integer portId, Integer id) {
        MooringCategory category = mooringCategoryRepository.findByIdAndZonePortId(id, portId)
                .orElseThrow(() -> new ResourceNotFoundException("MooringCategory not found"));
        return modelMapper.map(category, MooringCategoryInfoDto.class);
    }

    public MooringCategoryInfoDto createMooringCategory(Integer portId, MooringCategoryInfoDto dto) {
        Zone zone = zoneRepository.findByIdAndPortId(dto.getZoneId(), portId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        MooringDimension dimensions = mooringDimensionRepository.findById(dto.getDimensionsId())
                .orElseThrow(() -> new ResourceNotFoundException("MooringDimension not found"));

        MooringCategory category = modelMapper.map(dto, MooringCategory.class);
        category.setZone(zone);
        category.setDimensions(dimensions);

        return modelMapper.map(mooringCategoryRepository.save(category), MooringCategoryInfoDto.class);
    }

    public MooringCategoryInfoDto updateMooringCategory(Integer portId, Integer id, MooringCategoryInfoDto dto) {
        MooringCategory existing = mooringCategoryRepository.findByIdAndZonePortId(id, portId)
                .orElseThrow(() -> new ResourceNotFoundException("MooringCategory not found"));

        Zone zone = zoneRepository.findByIdAndPortId(dto.getZoneId(), portId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        MooringDimension dimensions = mooringDimensionRepository.findById(dto.getDimensionsId())
                .orElseThrow(() -> new ResourceNotFoundException("MooringDimension not found"));

        MooringCategory updated = modelMapper.map(dto, MooringCategory.class);
        updated.setId(existing.getId());
        updated.setZone(zone);
        updated.setDimensions(dimensions);

        return modelMapper.map(mooringCategoryRepository.save(updated), MooringCategoryInfoDto.class);
    }

    public void deleteMooringCategory(Integer id) {
        if (!mooringCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("MooringCategory not found");
        }
        mooringCategoryRepository.deleteById(id);
    }
}