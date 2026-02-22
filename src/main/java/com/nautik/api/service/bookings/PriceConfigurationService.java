package com.nautik.api.service.bookings;

import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceConfigurationService {

    private final PriceConfigurationRepository priceConfigurationRepository;
    private final ModelMapper modelMapper;

    public List<PriceConfigurationDto> getAll() {
        return priceConfigurationRepository.findAll()
                .stream()
                .map(pc -> modelMapper.map(pc, PriceConfigurationDto.class))
                .toList();
    }

    public PriceConfigurationDto getById(Integer id) {
        PriceConfiguration priceConfiguration = priceConfigurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceConfiguration not found with id: " + id));
        return modelMapper.map(priceConfiguration, PriceConfigurationDto.class);
    }

    public PriceConfigurationDto create(PriceConfigurationDto dto) {
        PriceConfiguration priceConfiguration = modelMapper.map(dto, PriceConfiguration.class);
        PriceConfiguration saved = priceConfigurationRepository.save(priceConfiguration);
        return modelMapper.map(saved, PriceConfigurationDto.class);
    }

    public PriceConfigurationDto update(Integer id, PriceConfigurationDto dto) {
        PriceConfiguration priceConfiguration = priceConfigurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceConfiguration not found with id: " + id));
        modelMapper.map(dto, priceConfiguration);
        PriceConfiguration updated = priceConfigurationRepository.save(priceConfiguration);
        return modelMapper.map(updated, PriceConfigurationDto.class);
    }

    public void delete(Integer id) {
        if (!priceConfigurationRepository.existsById(id)) {
            throw new ResourceNotFoundException("PriceConfiguration not found with id: " + id);
        }
        priceConfigurationRepository.deleteById(id);
    }
}