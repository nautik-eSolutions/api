package com.nautik.api.service.location;

import com.nautik.api.dto.location.ZoneDto;
import com.nautik.api.repository.location.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;

    public List<ZoneDto> findByPort(Long portId){
        return null;
    }

    public ZoneDto findById(Long portId, Long zoneId){
        return null;
    }
    public ZoneDto create(Long portId, ZoneDto zone){
        return null;
    }

    public ZoneDto update(Long portId, Long zoneId, ZoneDto zone ){
        return null;
    }

    public void delete(Long portId, Long zondeId){
    }


}
