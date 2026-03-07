package com.nautik.api.service.bookings;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.ForbiddenException;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public PriceConfigurationDto updatePriceConfiguration(Integer portId, Integer priceConfigurationId, PriceConfigurationDto dto) {
        validateOwnerShip(portId, priceConfigurationId);
        PriceConfiguration searchedPriceConfiguration = priceConfigurationRepository.findById(priceConfigurationId)
                .orElseThrow(() -> new EntityNotFoundException("PriceConfiguration not found"));

        PriceConfiguration providedPriceConfiguration = modelMapper.map(dto, PriceConfiguration.class);

        providedPriceConfiguration.setId(searchedPriceConfiguration.getId());
        providedPriceConfiguration.setPort(searchedPriceConfiguration.getPort());
        PriceConfiguration updated = priceConfigurationRepository.save(providedPriceConfiguration);
        return modelMapper.map(updated, PriceConfigurationDto.class);
    }

    public void deletePriceConfiguration(Integer portId, Integer priceConfigurationId) {
        validateOwnerShip(portId,priceConfigurationId);
        if (!priceConfigurationRepository.existsById(priceConfigurationId)) {
            throw new EntityNotFoundException("PriceConfiguration not found ");
        }
        priceConfigurationRepository.deleteById(priceConfigurationId);
    }

    public void validateOwnerShip(Integer portId, Integer priceConfigurationId){
        Port port = portRepository.findById(portId)
                .orElseThrow(
                        ()->new EntityNotFoundException("Port not found")
                );

        PriceConfiguration priceConfiguration = priceConfigurationRepository.findById(priceConfigurationId)
                .orElseThrow(()->new EntityNotFoundException("Price configuration not found"));

        if (!Objects.equals(port.getId(), priceConfiguration.getPort().getId())){
            throw new ForbiddenException("Forbidden why you entah, eh");
        }

    }

}