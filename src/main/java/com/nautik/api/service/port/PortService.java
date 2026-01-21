package com.nautik.api.service.port;

import com.nautik.api.dto.port.PortDto;
import com.nautik.api.repository.port.PortRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortService {


    PortRepository portRepository;
    ModelMapper modelMapper;

    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }


    public List<PortDto> findAll(){
        return portRepository.findAll()
                .stream()
                .map(port -> modelMapper.map(port, PortDto.class))
                .toList();

    }

    public PortDto findById(Long portId){
        return null;
    }
    public PortDto create(PortDto zone){
        return null;
    }

    public PortDto update(Long portId, PortDto zone ){
        return null;
    }

    public void delete(Long portId){
    }

}
