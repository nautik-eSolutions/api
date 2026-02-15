package com.nautik.api.controller.boats;


import com.nautik.api.dto.boat.BoatDto;
import com.nautik.api.dto.boat.BoatTypeDto;
import com.nautik.api.dto.boat.create.CreateBoatDto;
import com.nautik.api.service.boat.BoatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boats")
@RequiredArgsConstructor
public class BoatController {

    private final BoatService boatService;

    @GetMapping
    public ResponseEntity<List<BoatDto>> getAll(){
        List<BoatDto> boats = boatService.findAll();

        return ResponseEntity.ok(boats);
    }

    @GetMapping("/{idUser}/{boatId}")
    public ResponseEntity<BoatDto> getBoat(@PathVariable Long idUser, @PathVariable Long boatId) {
        BoatDto boat = boatService.findById(idUser, boatId);
        return ResponseEntity.ok(boat);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<BoatDto>> getAllBoats(@PathVariable Long idUser) {
        List<BoatDto> boats = boatService.findByUser(idUser);
        return ResponseEntity.ok(boats);
    }

    @PostMapping("/{idUser}")
    public ResponseEntity<BoatDto> createBoat(@PathVariable Long idUser, @RequestBody CreateBoatDto boatDto){
        BoatDto boat = boatService.createBoat(idUser, boatDto );
        return ResponseEntity.ok(boat);
    }

    @PatchMapping("/{idUser}/{idBoat}")
    public ResponseEntity<BoatDto> updateBoat(
            @PathVariable Long idUser,
            @RequestBody CreateBoatDto boatDto,
            @PathVariable Long idBoat){

        BoatDto dto = boatService.updateBoat(idUser,boatDto, idBoat);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{idBoat}")
    public ResponseEntity<BoatDto> deleteBoat( @PathVariable Long idBoat){
        boatService.deletBoat(idBoat);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<BoatTypeDto>> getBoatTypes(){
        return ResponseEntity.ok(boatService.getBoatTypes());


    }




}
