package com.nautik.api.controller.boats;


import com.nautik.api.dto.boat.BoatDto;
import com.nautik.api.dto.boat.create.CreateBoatDto;
import com.nautik.api.service.boat.BoatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        BoatDto boat = boatService.findByName(userName, boatName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userName}/")
    public ResponseEntity<List<BoatDto>> getAllBoats(@PathVariable String userName) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userName}/")
    public ResponseEntity<BoatDto> createBoat(@PathVariable String userName, @RequestBody CreateBoatDto boatDto){
        BoatDto boat = boatService.createBoat(userName, boatDto );
        return ResponseEntity.ok(boat);
    }

    @PatchMapping("/{userName}/{boatName}")
    public ResponseEntity<BoatDto> updateBoat(
            @PathVariable String userName,
            @RequestBody BoatDto boatDto,
            @PathVariable String boatName){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userName}/{boatName}")
    public ResponseEntity<BoatDto> updateBoat(@PathVariable String userName, @PathVariable String boatName){
        return ResponseEntity.ok().build();
    }




}
