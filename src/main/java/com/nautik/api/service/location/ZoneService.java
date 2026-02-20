package com.nautik.api.service.location;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.Zone;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.dto.location.create.CreateZoneDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;
    private final PortRepository portRepository;
    private final MooringCategoryRepository mooringCategoryRepository;

    public List<ZoneDto> findByPort(Long portId){

        return zoneRepository.findAllByPort_Id(Math.toIntExact(portId))
                .stream()
                .map(zone -> modelMapper.map(zone, ZoneDto.class))
                .toList();
    }

    public ZoneDto findById(Integer zoneId){

        return modelMapper.map(zoneRepository.findZoneById(zoneId).orElseThrow(()->new ResourceNotFoundException("Zone not found")), ZoneDto.class);
    }

    public ZoneDto create(Integer portId, CreateZoneDto zone){

        List<MooringCategory> categories = new ArrayList<>();
//        zone.getMooringCategoriesMooringNumber().forEach(cat -> {
//            categories.add(mooringCategoryRepository.findById(cat).orElseThrow(()->new ResourceNotFoundException("Mooring category not found")));
//        });
        Port port = portRepository.findById(portId).orElseThrow(()->new ResourceNotFoundException("Port not found"));
        Zone addZone = new Zone();
        addZone.setPort(port);
        addZone.setName(zone.getName());
        addZone.setDescription(zone.getDescription());
        addZone.setMooringCategories(categories);
        return modelMapper.map(zoneRepository.save(addZone), ZoneDto.class);
    }

    public ZoneDto update( Integer zoneId, CreateZoneDto zone, Long portId ){


        List<MooringCategory> categories = new ArrayList<>();
//        zone.getMooringCategoriesMooringNumber().forEach(cat -> {
//            categories.add(mooringCategoryRepository.findById(cat).orElseThrow(()->new ResourceNotFoundException("Mooring category not found")));
//        });
        Port port = portRepository.findById(Math.toIntExact(portId)).orElseThrow(()->new ResourceNotFoundException("Port not found"));
        Zone zoneProvided = new Zone();
        zoneProvided.setId(zoneId);
        zoneProvided.setPort(port);
        zoneProvided.setName(zone.getName());
        zoneProvided.setDescription(zone.getDescription());
        zoneProvided.setMooringCategories(categories);
        return modelMapper.map(zoneRepository.save(zoneProvided), ZoneDto.class);
    }

    public void delete(Integer zoneId){
        Zone zoneDelete = zoneRepository.findZoneById(zoneId).orElseThrow(()->new ResourceNotFoundException("Zone not found"));
        zoneRepository.delete(zoneDelete);

    }


}
