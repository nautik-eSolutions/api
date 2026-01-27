package com.nautik.api.controller.boats;


import com.nautik.api.dto.boat.BoatDto;
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

    @GetMapping("/{userName}/{boatName}")
    public ResponseEntity<BoatDto> getBoat(@PathVariable String userName, @PathVariable String boatName) {
        BoatDto boat = boatService.findByName(boatName, userName);
        return ResponseEntity.ok(boat);
    }

    @GetMapping("/{idUser}/")
    public ResponseEntity<List<BoatDto>> getAllBoats(@PathVariable Long idUser) {
        List<BoatDto> boats = boatService.findByUser(idUser);
        return ResponseEntity.ok(boats);
    }

    @PostMapping("/{userName}/")
    public ResponseEntity<BoatDto> createBoat(@PathVariable String userName, @RequestBody CreateBoatDto boatDto){
        BoatDto boat = boatService.createBoat(userName, boatDto );
        return ResponseEntity.ok(boat);
    }

    @PatchMapping("/{userName}/{idBoat}")
    public ResponseEntity<BoatDto> updateBoat(
            @PathVariable String userName,
            @RequestBody CreateBoatDto boatDto,
            @PathVariable Long idBoat){

        BoatDto dto = boatService.updateBoat(userName,boatDto, idBoat);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{userName}/{boatName}")
    public ResponseEntity<BoatDto> deleteBoat(@PathVariable String userName, @PathVariable String boatName){
        boatService.deletBoat(userName, boatName);
        return ResponseEntity.ok().build();
    }




}
