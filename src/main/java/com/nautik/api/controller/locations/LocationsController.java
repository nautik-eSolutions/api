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

    @GetMapping("/{communityId}/")
    public ResponseEntity<CommunityDto> getCommunity(@PathVariable Long communityId) {
        CommunityDto searchedCommunity = locationsService.getCommunity(communityId);
        return ResponseEntity.ok(searchedCommunity);
    }

    @PutMapping("/{communityId}/")
    public ResponseEntity<CommunityDto> updateCommunity(@PathVariable Long communityId, @RequestBody CommunityDto communityDto) {
        CommunityDto updatedCommunity =  locationsService.updateCommunity(communityId,communityDto);

        return ResponseEntity.ok(updatedCommunity);
    }

    @DeleteMapping("/{communityId}/")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long communityId) {
        locationsService.deleteCommunity(communityId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{communityId}/")
    public ResponseEntity<CityDto> createCity(@PathVariable Long communityId, @RequestBody CityDto cityDto) {
        CityDto createdCity =  locationsService.createCity(cityDto,communityId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);

    }

    @GetMapping("/{communityId}/cities")
    public ResponseEntity<List<CityDto>> getAllCities(@PathVariable Long communityId) {

        List<CityDto> searchedCities =  locationsService.getAllCitiesByCommunityId(communityId);


        return ResponseEntity.ok(searchedCities);
    }

    @GetMapping("/{communityId}/{cityId}")
    public ResponseEntity<CityDto> getCity(@PathVariable Long communityId, @PathVariable Long cityId) {
        CityDto searchedCity = locationsService.getCityById(cityId,communityId);
        return ResponseEntity.ok(searchedCity);
    }

    @PutMapping("/{communityId}/{cityId}")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long communityId, @PathVariable Long cityId, @RequestBody CityDto cityDto) {
        CityDto updatedCity = locationsService.updateCity(cityDto,communityId, cityId);

        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{communityId}/{cityId}")
    public ResponseEntity<CityDto> deleteCity(@PathVariable Long communityId, @PathVariable Long cityId) {
        locationsService.deleteCity(cityId,communityId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
