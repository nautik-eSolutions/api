package com.nautik.api.controller.ports;

import com.nautik.api.dto.port.PortDto;
import com.nautik.api.service.port.PortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ports")
@RequiredArgsConstructor
public class PortController {

    private final PortService portService;

    @GetMapping
    public ResponseEntity<List<PortDto>> getAllPorts() {
        List<PortDto> allPorts = portService.findAll();
        return ResponseEntity.ok(allPorts);
    }

    @GetMapping("/{portName}")
    public ResponseEntity<PortDto> getPortById(
            @PathVariable String portName) {
        PortDto port = portService.findByName(portName);
        return ResponseEntity.ok(port);
    }

    @PostMapping
    public ResponseEntity<PortDto> createPort(@RequestBody PortDto dto) {
        PortDto portCreated  = portService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(portCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortDto> updatePort(
            @PathVariable Long id,
            @RequestBody PortDto dto
    ) {
        PortDto updatePort = portService.update(id, dto);
        return ResponseEntity.ok(updatePort);
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<Void> deletePort(@PathVariable Long id) {

        portService.delete(id);
        return ResponseEntity.ok().build();
    }
}

