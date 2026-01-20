package com.nautik.api.service.location;

import com.nautik.api.domain.Zone;
import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.repository.location.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;

    public List<ZoneDto> findByPort(String portName){

        return zoneRepository.findAllByPort_Name(portName)
                .stream()
                .map(zone -> modelMapper.map(zone, ZoneDto.class))
                .toList();
    }

    public ZoneDto findById(Integer zoneId){
        return modelMapper.map(zoneRepository.findZoneById(zoneId), ZoneDto.class);
    }

    public ZoneDto create(Long portId, ZoneDto zone){
        Zone addZone = modelMapper.map(zone, Zone.class);
        return modelMapper.map(zoneRepository.save(addZone), ZoneDto.class);
    }

    public ZoneDto update( Integer zoneId, ZoneDto zone ){
        Zone zoneUpdate = zoneRepository.findZoneById(zoneId).orElseThrow();
        Zone zoneProvided = modelMapper.map(zone, Zone.class);
        zoneProvided.setId(zoneUpdate.getId());
        return modelMapper.map(zoneRepository.save(zoneProvided), ZoneDto.class);
    }

    public void delete(Integer zoneId){
        Zone zoneDelete = zoneRepository.findZoneById(zoneId).orElseThrow();
        zoneRepository.delete(zoneDelete);

    }


}
