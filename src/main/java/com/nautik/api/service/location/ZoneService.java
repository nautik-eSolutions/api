package com.nautik.api.service.location;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.Zone;
import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.port.PortRepository;
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
    private final PortRepository portRepository;

    public List<ZoneDto> findByPort(String portName){
        String name = portName.replace("_", " ");

        return zoneRepository.findAllByPort_Name(name)
                .stream()
                .map(zone -> modelMapper.map(zone, ZoneDto.class))
                .toList();
    }

    public ZoneDto findById(Integer zoneId, String portName){
        String name = portName.replace("_", " ");
        System.out.println(name);
        Port port = portRepository.findByNameIgnoreCase(name).orElseThrow();
        System.out.println(port.getName());
        return modelMapper.map(zoneRepository.findZoneByIdAndPort(zoneId, port).orElseThrow(), ZoneDto.class);
    }

    public ZoneDto create(String portName, ZoneDto zone){
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
