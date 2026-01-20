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
public class LocationsService {

    public final ModelMapper modelMapper;
    public final CityRepository cityRepository;
    public final CommunityRepository communityRepository;


    public CommunityDto createCommunity(CommunityDto communityDto) {
        Community providedCommunity = modelMapper.map(communityDto, Community.class);

        return modelMapper.map(communityRepository.save(providedCommunity), CommunityDto.class);
    }

    public CommunityDto updateCommunity(String communityName,CommunityDto communityDto) {
        Community community = communityRepository.findByName(communityName).orElseThrow();

        Community providedCommunity = modelMapper.map(communityDto, Community.class);
        providedCommunity.setId(community.getId());

        return modelMapper.map(communityRepository.save(providedCommunity), CommunityDto.class);
    }


    public CommunityDto getCommunity(String name) {
        Community searchedCommunity = communityRepository.findByName(name).orElseThrow();

        return modelMapper.map(searchedCommunity, CommunityDto.class);

    }

    public List<CommunityDto> getAllCommunities() {
        List<Community> communities = communityRepository.findAll();

        return communities
                .stream()
                .map(
                        community ->
                                modelMapper.map(community, CommunityDto.class))
                .collect(Collectors.toList());
    }


    public void deleteCommunity(String name) {
        Community searchedCommunity = communityRepository.findByName(name).orElseThrow();
        communityRepository.delete(searchedCommunity);
    }


    public CityDto getCityByName(String cityName, String communityName) {
        City city = cityRepository.findByNameAndCommunity_Name(cityName, communityName).orElseThrow();
        return modelMapper.map(city, CityDto.class);
    }

    public List<CityDto> getAllCitiesByCommunityName(String communityName) {
        List<City> cities = cityRepository.getAllByCommunity_Name(communityName);
        return cities.stream().map(city -> modelMapper.map(city, CityDto.class)).collect(Collectors.toList());
    }

    public CityDto createCity(CityDto cityDto, String communityName) {
        Community searchedCommunity = communityRepository.findByName(communityName).orElseThrow();

        City providedCity = modelMapper.map(cityDto, City.class);
        providedCity.setCommunity(searchedCommunity);
        return modelMapper.map(cityRepository.save(providedCity), CityDto.class);
    }

    public CityDto updateCity(CityDto cityDto, String communityName) {
        Community searchedCommunity = communityRepository
                .findByName(communityName).orElseThrow();


        City searchedCity = cityRepository
                .findByNameAndCommunity_Name(cityDto.getName(), communityName)
                .orElseThrow();

        City providedCity = modelMapper.map(cityDto, City.class);

        providedCity.setId(searchedCity.getId());
        providedCity.setCommunity(searchedCommunity);

        return modelMapper.map(cityRepository.save(providedCity), CityDto.class);
    }

    public void deleteCity(String  cityName, String communityName) {
        City searchedCity = cityRepository
                .findByNameAndCommunity_Name(cityName, communityName)
                .orElseThrow();

        cityRepository.delete(searchedCity);
    }


}
