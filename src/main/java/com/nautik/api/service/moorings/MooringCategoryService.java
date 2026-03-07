package com.nautik.api.service.moorings;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.exceptions.ForbiddenException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.MooringDimension;
import com.nautik.api.domain.Zone;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringCategoryInfoDto;
import com.nautik.api.dto.mooring.MooringCategoryPriceConfigurationDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;

import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MooringCategoryService {

    private final MooringCategoryRepository mooringCategoryRepository;
    private final MooringDimensionRepository mooringDimensionRepository;
    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;
    private final PortRepository portRepository;
    private final PriceConfigurationRepository priceConfigurationRepository;

    public List<MooringCategoryInfoDto> getAllByPortId(Integer portId) {
        return mooringCategoryRepository.findAllByZonePortId(portId).stream().map(mc -> modelMapper.map(mc, MooringCategoryInfoDto.class)).toList();
    }

    public MooringCategoryDto getById(Integer portId, Integer id) {
        MooringCategory category = mooringCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("MooringCategory not found"));
        return modelMapper.map(category, MooringCategoryDto.class);
    }

    public MooringCategoryInfoDto createMooringCategory(Integer portId, MooringCategoryInfoDto dto) {
        Zone zone = zoneRepository.findByIdAndPortId(dto.getZoneId(), portId).orElseThrow(() -> new EntityNotFoundException("Zone not found"));

        MooringDimension dimensions = mooringDimensionRepository.findById(dto.getDimensionsId()).orElseThrow(() -> new EntityNotFoundException("MooringDimension not found"));

        MooringCategory category = modelMapper.map(dto, MooringCategory.class);
        category.setZone(zone);
        category.setDimensions(dimensions);

        return modelMapper.map(mooringCategoryRepository.save(category), MooringCategoryInfoDto.class);
    }

    public MooringCategoryInfoDto updateMooringCategory(Integer portId, Integer id, MooringCategoryInfoDto dto) {
        MooringCategory existing = mooringCategoryRepository.findByIdAndZonePortId(id, portId).orElseThrow(() -> new EntityNotFoundException("MooringCategory not found"));

        Zone zone = zoneRepository.findByIdAndPortId(dto.getZoneId(), portId).orElseThrow(() -> new EntityNotFoundException("Zone not found"));

        MooringDimension dimensions = mooringDimensionRepository.findById(dto.getDimensionsId()).orElseThrow(() -> new EntityNotFoundException("MooringDimension not found"));

        MooringCategory updated = modelMapper.map(dto, MooringCategory.class);
        updated.setId(existing.getId());
        updated.setZone(zone);
        updated.setDimensions(dimensions);

        return modelMapper.map(mooringCategoryRepository.save(updated), MooringCategoryInfoDto.class);
    }

    public void deleteMooringCategory(Integer id) {
        if (!mooringCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("MooringCategory not found");
        }
        mooringCategoryRepository.deleteById(id);
    }


    public MooringCategoryDto assingPriceConfigurationToMooringCategory(
            Integer portId, Integer priceConfigurationId, Integer mooringCategoryId){
        Port port = portRepository
                .findById(portId).orElseThrow(
                        ()->new EntityNotFoundException("Port not found")
                );

        PriceConfiguration priceConfiguration = port
                .getPriceConfigurations()
                .stream()
                .filter(pc-> Objects.equals(pc.getId(), priceConfigurationId))
                .findFirst().orElseThrow(()->new EntityNotFoundException("Price configuration not found"));
        MooringCategory mooringCategory = mooringCategoryRepository
                .findById(mooringCategoryId).orElseThrow(
                        ()->new EntityNotFoundException("Mooring category not found")
                );
        if (!Objects.equals(mooringCategory.getZone().getPort().getId(), port.getId())){
            throw new ForbiddenException("You don't have access to this resource");
        }

        priceConfiguration.getMooringCategories().add(mooringCategory);
        priceConfigurationRepository.save(priceConfiguration);
        return modelMapper.map(mooringCategoryRepository.save(mooringCategory),MooringCategoryDto.class);
    }

    public MooringCategoryDto deAssignPriceConfigurationFromMooringCategory
            (Integer portId, Integer priceConfigurationId, Integer mooringCategoryId){
        Port port = portRepository
                .findById(portId).orElseThrow(
                        ()->new EntityNotFoundException("Port not found")
                );

        PriceConfiguration priceConfiguration = port
                .getPriceConfigurations()
                .stream()
                .filter(pc-> Objects.equals(pc.getId(), priceConfigurationId))
                .findFirst().orElseThrow(()->new EntityNotFoundException("MooringD"));
        MooringCategory mooringCategory = mooringCategoryRepository
                .findById(mooringCategoryId).orElseThrow(
                        ()->new EntityNotFoundException("Mooring category not found")
                );
        if (!Objects.equals(mooringCategory.getZone().getPort().getId(), port.getId())){
            throw new ForbiddenException("You don't have access to this resource");
        }

        priceConfiguration.getMooringCategories().remove(mooringCategory);

        priceConfigurationRepository.save(priceConfiguration);
        return modelMapper.map(mooringCategoryRepository.save(mooringCategory),MooringCategoryDto.class);
    }

}