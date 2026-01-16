package com.nautik.api.controller.boats;


import com.nautik.api.dto.boat.BoatDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boats")
public class BoatController {

    @GetMapping("/{userName}/{boatName}")
    public ResponseEntity<BoatDto> getBoat(@PathVariable String userName, @PathVariable String boatName) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userName}/")
    public ResponseEntity<List<BoatDto>> getAllBoats(@PathVariable String userName) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userName}/")
    public ResponseEntity<BoatDto> createBoat(@PathVariable String userName, @RequestBody BoatDto boatDto){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userName}/")
    public ResponseEntity<BoatDto> updateBoat(@PathVariable String userName, @RequestBody BoatDto boatDto){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userName}/{boatName}")
    public ResponseEntity<BoatDto> updateBoat(@PathVariable String userName, @PathVariable String boatName){
        return ResponseEntity.ok().build();
    }




}
