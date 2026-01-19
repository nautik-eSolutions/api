package com.nautik.api.service.location;

import com.nautik.api.dto.location.CityDto;
import com.nautik.api.repository.location.CityRepository;
import com.nautik.api.repository.location.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    public final ModelMapper modelMapper;
    public final CityRepository cityRepository;
    public final CommunityRepository communityRepository;

    public CityDto getCityByName(String name){

    }




}
