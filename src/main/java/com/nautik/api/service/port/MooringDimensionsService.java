package com.nautik.api.service.port;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.moorings.MooringDimension;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.create.MooringDimensionCreateDto;
import com.nautik.api.repository.moorings.MooringDimensionRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MooringDimensionsService {

    private final MooringDimensionRepository dimensionRepository;
    private final PortRepository portRepository;
    private final ModelMapper modelMapper;



    public List<MooringDimensionDto> getAllMooringsDimensions(){
        return dimensionRepository.findAll()
                .stream()
                .map((element) -> modelMapper
                        .map(element, MooringDimensionDto.class))
                .toList();
    }

    public List<MooringDimensionDto> getAllMooringsDimensionsByPort(Integer portId){
        return dimensionRepository.findByPortId(portId)
                .stream()
                .map(
                        dimensions -> modelMapper.map(dimensions, MooringDimensionDto.class)
                ).toList();
    }

    public MooringDimensionDto createMooringDimension(Integer portId, MooringDimensionCreateDto mooringDimensionDto) {

        MooringDimension providedMooringDimension = modelMapper.map(mooringDimensionDto, MooringDimension.class);

        Port providedPort = portRepository
                .findById(portId).orElseThrow(
                        ()->new ResourceNotFoundException("Port not found")
                );

        providedMooringDimension.setPort(providedPort);

        return modelMapper.map( dimensionRepository.save(providedMooringDimension), MooringDimensionDto.class);
    }


    public MooringDimensionDto updateMooringDimension(Integer mooringDimensionID, MooringDimensionCreateDto mooringDimensionDto) {

        MooringDimension providedMooringDimension = modelMapper.map(mooringDimensionDto, MooringDimension.class);
        providedMooringDimension
                .setId(
                        dimensionRepository
                                .findById(mooringDimensionID)
                                .orElseThrow(
                                        ()->new ResourceNotFoundException("Dimension not found")
                                )
                                .getId()
                );

        return modelMapper.map( dimensionRepository.save(providedMooringDimension), MooringDimensionDto.class);
    }

    public void deleteMooringDimension(Integer mooringDimensionID) {

        MooringDimension mooringDimension = dimensionRepository
                .findById(mooringDimensionID)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Dimension not found")
                );


        dimensionRepository.delete(mooringDimension);
    }






}
