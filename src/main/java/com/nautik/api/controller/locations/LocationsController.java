package com.nautik.api.controller.locations;


import com.nautik.api.dto.location.CityDto;
import com.nautik.api.dto.location.CommunityDto;
import com.nautik.api.service.location.LocationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations/")
@RequiredArgsConstructor
public class LocationsController {

    private final LocationsService locationsService;

    @GetMapping
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        List<CommunityDto> communities = locationsService.getAllCommunities();

        return ResponseEntity.ok(communities);
    }

    @PostMapping
    public ResponseEntity<CommunityDto> createCommunity(@RequestBody CommunityDto communityDto) {
        CommunityDto savedCommunity = locationsService.createCommunity(communityDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommunity);
    }

    @GetMapping("/{community}/")
    public ResponseEntity<CommunityDto> getCommunity(@PathVariable String community) {
        CommunityDto searchedCommunity = locationsService.getCommunity(community);
        return ResponseEntity.ok(searchedCommunity);
    }

    @PutMapping("/{community}/")
    public ResponseEntity<CommunityDto> updateCommunity(@PathVariable String community, @RequestBody CommunityDto communityDto) {
        CommunityDto updatedCommunity =  locationsService.updateCommunity(community,communityDto);

        return ResponseEntity.ok(updatedCommunity);
    }

    @DeleteMapping("/{community}/")
    public ResponseEntity<Void> deleteCommunity(@PathVariable String community) {
        locationsService.deleteCommunity(community);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{community}/")
    public ResponseEntity<CityDto> createCity(@PathVariable String community, @RequestBody CityDto cityDto) {
        CityDto createdCity =  locationsService.createCity(cityDto,community);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);

    }

    @GetMapping("/{community}/cities")
    public ResponseEntity<List<CityDto>> getAllCities(@PathVariable String community) {

        List<CityDto> searchedCities =  locationsService.getAllCitiesByCommunityName(community);


        return ResponseEntity.ok(searchedCities);
    }

    @GetMapping("/{community}/{city}")
    public ResponseEntity<CityDto> getCity(@PathVariable String community, @PathVariable String city) {
        CityDto searchedCity = locationsService.getCityByName(city,community);
        return ResponseEntity.ok(searchedCity);
    }

    @PutMapping("/{community}/{city}")
    public ResponseEntity<CityDto> updateCity(@PathVariable String community, @PathVariable String city, @RequestBody CityDto cityDto) {
        CityDto updatedCity = locationsService.updateCity(cityDto,community);

        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{community}/{city}")
    public ResponseEntity<CityDto> deleteCity(@PathVariable String community, @PathVariable String city) {
        locationsService.deleteCity(city,community);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
