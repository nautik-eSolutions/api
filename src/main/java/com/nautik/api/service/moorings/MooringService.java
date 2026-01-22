package com.nautik.api.service.moorings;


import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
}
