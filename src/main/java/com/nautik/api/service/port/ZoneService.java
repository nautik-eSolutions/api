package com.nautik.api.service.port;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.Zone;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.exceptions.ZoneConstraintViolationException;
import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.dto.location.create.CreateZoneDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;
    private final PortRepository portRepository;
    private final MooringCategoryRepository mooringCategoryRepository;

    public List<ZoneDto> findByPort(Long portId) {

        return zoneRepository.findAllByPort_Id(Math.toIntExact(portId))
                .stream()
                .map(zone -> modelMapper.map(zone, ZoneDto.class))
                .toList();
    }

    public ZoneDto findById(Integer zoneId) {

        return modelMapper.map(zoneRepository.findZoneById(zoneId).orElseThrow(() -> new ResourceNotFoundException("Zone not found")), ZoneDto.class);
    }

    public ZoneDto create(Integer portId, CreateZoneDto zone) {
        Port port = portRepository.findById(portId).orElseThrow(() -> new ResourceNotFoundException("Port not found"));
        Zone addZone = new Zone();
        addZone.setPort(port);
        addZone.setName(zone.getName());
        addZone.setDescription(zone.getDescription());
        return modelMapper.map(zoneRepository.save(addZone), ZoneDto.class);
    }

    public ZoneDto update(Integer zoneId, CreateZoneDto zone) {
        Zone searchedZone = zoneRepository.findZoneById(zoneId).orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        Zone zoneProvided = modelMapper.map(zone, Zone.class);
        zoneProvided.setId(searchedZone.getId());
        zoneProvided.setPort(searchedZone.getPort());
        return modelMapper.map(zoneRepository.save(zoneProvided), ZoneDto.class);
    }

    public void delete(Integer zoneId) {
        Zone zoneDelete = zoneRepository.findZoneById(zoneId).orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
        if (!zoneDelete.getMooringCategories().isEmpty()) {
            throw new ZoneConstraintViolationException();
        }

        zoneRepository.delete(zoneDelete);

    }


}
