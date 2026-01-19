package com.nautik.api.controller.ports;

import com.nautik.api.dto.port.PortDto;
import com.nautik.api.service.port.PortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ports")
@RequiredArgsConstructor
public class PortController {

    private final PortService portService;

    @GetMapping
    public List<PortDto> getAllPorts() {
        return portService.findAll();
    }

    @GetMapping("/{id}")
    public PortDto getPortById(@PathVariable Long id) {
        return portService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PortDto createPort(@RequestBody PortDto dto) {
        return portService.create(dto);
    }

    @PutMapping("/{id}")
    public PortDto updatePort(
            @PathVariable Long id,
            @RequestBody PortDto dto
    ) {
        return portService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePort(@PathVariable Long id) {
        portService.delete(id);
    }
}

