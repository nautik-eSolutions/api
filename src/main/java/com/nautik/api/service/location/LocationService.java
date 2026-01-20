package com.nautik.api.service.location;

import com.nautik.api.domain.City;
import com.nautik.api.domain.Community;
import com.nautik.api.dto.location.CityDto;
import com.nautik.api.dto.location.CommunityDto;
import com.nautik.api.repository.location.CityRepository;
import com.nautik.api.repository.location.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    public final ModelMapper modelMapper;
    public final CityRepository cityRepository;
    public final CommunityRepository communityRepository;



    public CommunityDto createCommunity(CommunityDto communityDto){
        Community providedCommunity =  modelMapper.map(communityDto, Community.class);
        CommunityDto savedCommunity =  modelMapper.map(communityRepository.save(providedCommunity), CommunityDto.class);

        return savedCommunity;
    }

    public CommunityDto updateCommunity(CommunityDto communityDto){
        Community community = communityRepository.findByName(communityDto.getName()).orElseThrow();

        Community providedCommunity =  modelMapper.map(communityDto, Community.class);
        providedCommunity.setId(community.getId());

        CommunityDto updatedCommunity =  modelMapper.map(communityRepository.save(providedCommunity), CommunityDto.class);

        return updatedCommunity;
    }


    public CommunityDto getCommunity(String name){
        Community searchedCommunity =  communityRepository.findByName(name).orElseThrow();

        return modelMapper.map(searchedCommunity, CommunityDto.class);

    }

    public List<CommunityDto> getAllCommunities(){
        List<Community> communities = communityRepository.findAll();

        return communities
                .stream()
                .map(
                        community ->
                                modelMapper.map(community, CommunityDto.class))
                .collect(Collectors.toList());
    }


    public void deleteCommunity(String name){
        Community searchedCommunity = communityRepository.findByName(name).orElseThrow();
        communityRepository.delete(searchedCommunity);
    }



    public CityDto getCityByName(String name){
        City city = cityRepository.getByNameContainsIgnoreCase(name).orElseThrow();
        return modelMapper.map(city, CityDto.class);
    }

    public CityDto createCity(String cityName, String communityName){

    }




}
