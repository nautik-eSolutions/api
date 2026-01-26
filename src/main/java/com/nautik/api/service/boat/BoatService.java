package com.nautik.api.service.boat;

import com.nautik.api.domain.Boat;
import com.nautik.api.domain.BoatType;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.boat.BoatDto;
import com.nautik.api.dto.boat.create.CreateBoatDto;
import com.nautik.api.repository.boat.BoatRepository;
import com.nautik.api.repository.boat.BoatTypeRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoatService {

    private final ModelMapper modelMapper;
    private final BoatRepository boatRepository;
    private final BoatTypeRepository boatTypeRepository;
    private final UserRepository userRepository;


    public BoatDto findByName (String name, String userName){
        return modelMapper.map(boatRepository.findAllByNameAndUser_UserName(name, userName), BoatDto.class);
    }

    public List<BoatDto> findAll(){
        return boatRepository.findAll().stream().map(boat -> modelMapper.map(boat, BoatDto.class)).toList();

    }
    public BoatDto createBoat(String username, CreateBoatDto boatDto){
        User user = userRepository.getByUserName(username).orElseThrow();
        BoatType boatType = boatTypeRepository.findByName(boatDto.getBoatType());
        Boat boat = new Boat();
        boat.setBeam(boatDto.getBeam());
        boat.setDraft(boatDto.getDraft());
        boat.setLength(boatDto.getLength());
        boat.setName(boatDto.getName());
        boat.setRegistryNumber(boatDto.getRegistryNumber());
        boat.setUser(user);
        boat.setBoatType(boatType);

        return modelMapper.map(boatRepository.save(boat), BoatDto.class);


    }

//    public BoatDto updateBoat(){}
//
//    public void deletBoat(){
//
//    }


}
