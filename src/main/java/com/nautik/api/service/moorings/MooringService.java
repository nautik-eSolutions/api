package com.nautik.api.service.moorings;


import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MooringService {

    public final MooringRepository mooringRepository;
    public final ModelMapper modelMapper;

    public List<MooringDto> findAll(){
        return mooringRepository.findAll().stream().map(mooring -> modelMapper.map(mooring, MooringDto.class)).toList();
    }

    public MooringDto findById(long mooringId){
        return modelMapper.map(mooringRepository.findById(mooringId), MooringDto.class);
    }

    public List<MooringDto> findAllByPort(String portName){
        String port = portName.replace("_"," ");
        return mooringRepository.findAllByMooringCategory_Zone_Port_NameIgnoreCase(port)
                .stream()
                .map(mooring -> modelMapper.map(mooring, MooringDto.class))
                .toList();
    }
    public MooringDto createMooring(String portName, MooringDto dto){
        Mooring mooring = modelMapper.map(dto, Mooring.class);
        return modelMapper.map(mooringRepository.save(mooring), MooringDto.class);

    }
    public void delete(long id){
        Mooring mooring = mooringRepository.findById(id).orElseThrow();
        mooringRepository.delete(mooring);
    }
}
