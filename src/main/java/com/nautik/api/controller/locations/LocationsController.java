package com.nautik.api.controller.locations;


import com.nautik.api.dto.location.CityDto;
import com.nautik.api.dto.location.CommunityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations/")
public class LocationsController {

    @GetMapping
    public ResponseEntity<List<CommunityDto>>getAllCommunities(){
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<CommunityDto>createCommunity(
            @RequestBody CommunityDto communityDto
    ){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{community}/")
    public ResponseEntity<CityDto>getCommunity(
            @PathVariable String community,
    ){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{community}/")
    public ResponseEntity<CityDto>updateCommunity(
            @PathVariable String community,
            @RequestBody CommunityDto communityDto
    ){
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{community}/")
    public ResponseEntity<CityDto>deleteCommunity(
            @PathVariable String community
    ){
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{community}/")
    public ResponseEntity<CityDto>createCity(
            @PathVariable String community,
            @RequestBody CityDto cityDto
    ){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{community}/")
    public ResponseEntity<List<CityDto>>getAllCities(
            @PathVariable String community){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{community}/{city}")
    public ResponseEntity<CityDto>getCity(
            @PathVariable String community,
            @PathVariable String city
    ){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{community}/{city}")
    public ResponseEntity<CityDto>updateCity(
            @PathVariable String community,
            @PathVariable String city,
            @RequestBody CityDto cityDto
    ){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{community}/{city}")
    public ResponseEntity<CityDto>deleteCity(
            @PathVariable String community,
            @PathVariable String city
    ){
        return ResponseEntity.ok().build();
    }




}
