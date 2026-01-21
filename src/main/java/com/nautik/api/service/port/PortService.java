package com.nautik.api.service.port;

import com.nautik.api.domain.Port;
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
        return modelMapper.map(portRepository.findById(Math.toIntExact(portId)), PortDto.class);
    }

    public PortDto create(PortDto port){
        Port addPort = modelMapper.map(port, Port.class);
        return modelMapper.map(portRepository.save(addPort), PortDto.class);
    }

    public PortDto update(Long portId, PortDto port ){
        Port updatePort = portRepository.findById(Math.toIntExact(portId)).orElseThrow();
        Port providePort = modelMapper.map(port, Port.class);
        providePort.setId(updatePort.getId());
        return modelMapper.map(portRepository.save(providePort), PortDto.class);
    }

    public void delete(Long portId){
        Port deletePort = portRepository.findById(Math.toIntExact(portId)).orElseThrow();
        portRepository.delete(deletePort);
    }

}
