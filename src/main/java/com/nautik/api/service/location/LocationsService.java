package com.nautik.api.service.location;

import com.nautik.api.domain.City;
import com.nautik.api.domain.Community;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
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

    public CommunityDto updateCommunity(Long communityId,CommunityDto communityDto) {
        Community community = communityRepository.findById(Math.toIntExact(communityId)).orElseThrow(()->new ResourceNotFoundException("Community not found"));

        Community providedCommunity = modelMapper.map(communityDto, Community.class);
        providedCommunity.setId(community.getId());

        return modelMapper.map(communityRepository.save(providedCommunity), CommunityDto.class);
    }


    public CommunityDto getCommunity(Long id) {
        Community searchedCommunity = communityRepository.findById(Math.toIntExact(id)).orElseThrow(()->new ResourceNotFoundException("Community not found"));

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


    public void deleteCommunity(Long id) {
        Community searchedCommunity = communityRepository.findById(Math.toIntExact(id)).orElseThrow(()->new ResourceNotFoundException("Community not found"));
        communityRepository.delete(searchedCommunity);
    }


    public CityDto getCityByName(String cityName, String communityName) {
        City city = cityRepository.findByNameAndCommunity_Name(cityName, communityName).orElseThrow(()->new ResourceNotFoundException("City not found"));
        return modelMapper.map(city, CityDto.class);
    }

    public List<CityDto> getAllCitiesByCommunityId(Long communityId) {
        List<City> cities = cityRepository.findAllByCommunity_Id(Math.toIntExact(communityId));
        return cities.stream().map(city -> modelMapper.map(city, CityDto.class)).collect(Collectors.toList());
    }

    public CityDto createCity(CityDto cityDto, Long communityId) {
        Community searchedCommunity = communityRepository.findById(Math.toIntExact(communityId)).orElseThrow(()->new ResourceNotFoundException("Community not found")   );

        City providedCity = modelMapper.map(cityDto, City.class);
        providedCity.setCommunity(searchedCommunity);
        return modelMapper.map(cityRepository.save(providedCity), CityDto.class);
    }

    public CityDto updateCity(CityDto cityDto, Long communityId, Long cityId) {
        Community searchedCommunity = communityRepository
                .findById(Math.toIntExact(communityId)).orElseThrow(()->new ResourceNotFoundException("Community not found"));


        City searchedCity = cityRepository
                .findByIdAndCommunity_Id(Math.toIntExact(cityId), Math.toIntExact(communityId))
                .orElseThrow(()->new ResourceNotFoundException("City not found"));

        City providedCity = modelMapper.map(cityDto, City.class);

        providedCity.setId(searchedCity.getId());
        providedCity.setCommunity(searchedCommunity);

        return modelMapper.map(cityRepository.save(providedCity), CityDto.class);
    }

    public void deleteCity(Long  cityId, Long communityId) {
        City searchedCity = cityRepository
                .findByIdAndCommunity_Id(Math.toIntExact(cityId), Math.toIntExact(communityId))
                .orElseThrow(()->new ResourceNotFoundException("City not found"));

        cityRepository.delete(searchedCity);
    }


    public CityDto getCityById(Long cityId, Long communityId) {
        City city = cityRepository.findByIdAndCommunity_Id(Math.toIntExact(cityId), Math.toIntExact(communityId)).orElseThrow();
        return modelMapper.map(city, CityDto.class);
    }
}
