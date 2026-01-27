package com.nautik.api.service.location;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.Zone;
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

    public ZoneDto create(String portName, CreateZoneDto zone){
        String name = portName.replace("_", " ");

        List<MooringCategory> categories = new ArrayList<>();
        zone.getMooringCategoriesMooringNumber().forEach(cat -> {
            categories.add(mooringCategoryRepository.findById(cat).orElseThrow());
        });
        Port port = portRepository.findByNameIgnoreCase(name).orElseThrow();
        Zone addZone = new Zone();
        addZone.setPort(port);
        addZone.setName(zone.getName());
        addZone.setDescription(zone.getDescription());
        addZone.setMooringCategories(categories);
        return modelMapper.map(zoneRepository.save(addZone), ZoneDto.class);
    }

    public ZoneDto update( Integer zoneId, CreateZoneDto zone, String portName ){
        String name = portName.replace("_", " ");

        List<MooringCategory> categories = new ArrayList<>();
        zone.getMooringCategoriesMooringNumber().forEach(cat -> {
            categories.add(mooringCategoryRepository.findById(cat).orElseThrow());
        });
        Port port = portRepository.findByNameIgnoreCase(name).orElseThrow();
        Zone zoneProvided = new Zone();
        zoneProvided.setId(zoneId);
        zoneProvided.setPort(port);
        zoneProvided.setName(zone.getName());
        zoneProvided.setDescription(zone.getDescription());
        zoneProvided.setMooringCategories(categories);
        return modelMapper.map(zoneRepository.save(zoneProvided), ZoneDto.class);
    }

    public void delete(Integer zoneId){
        Zone zoneDelete = zoneRepository.findZoneById(zoneId).orElseThrow();
        zoneRepository.delete(zoneDelete);

    }


}
