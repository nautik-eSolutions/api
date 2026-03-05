package com.nautik.api.service.bookings;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceConfigurationService {

    private final PriceConfigurationRepository priceConfigurationRepository;
    private final PortRepository portRepository;
    private final ModelMapper modelMapper;

    public List<PriceConfigurationDto> getAllByPortId(Integer portId) {
        return priceConfigurationRepository.findAllByPortId(portId)
                .stream()
                .map(pc -> modelMapper.map(pc, PriceConfigurationDto.class))
                .toList();
    }

    public PriceConfigurationDto createPriceConfiguration(Integer portId ,PriceConfigurationDto dto) {
        PriceConfiguration priceConfiguration = modelMapper.map(dto, PriceConfiguration.class);
        Port port = portRepository.findById(portId).orElseThrow(()->new EntityNotFoundException("Port not found"));

        priceConfiguration.setPort(port);
        PriceConfiguration createdPort = priceConfigurationRepository.save(priceConfiguration);
        return modelMapper.map(createdPort, PriceConfigurationDto.class);
    }

    public PriceConfigurationDto updatePriceConfiguration(Integer priceConfigurationId, PriceConfigurationDto dto) {
        PriceConfiguration searchedPriceConfiguration = priceConfigurationRepository.findById(priceConfigurationId)
                .orElseThrow(() -> new EntityNotFoundException("PriceConfiguration not found"));

        PriceConfiguration providedPriceConfiguration = modelMapper.map(dto, PriceConfiguration.class);

        providedPriceConfiguration.setId(searchedPriceConfiguration.getId());
        providedPriceConfiguration.setPort(searchedPriceConfiguration.getPort());
        PriceConfiguration updated = priceConfigurationRepository.save(providedPriceConfiguration);
        return modelMapper.map(updated, PriceConfigurationDto.class);
    }

    public void deletePriceConfiguration(Integer priceConfigurationId) {
        if (!priceConfigurationRepository.existsById(priceConfigurationId)) {
            throw new EntityNotFoundException("PriceConfiguration not found ");
        }
        priceConfigurationRepository.deleteById(priceConfigurationId);
    }
}