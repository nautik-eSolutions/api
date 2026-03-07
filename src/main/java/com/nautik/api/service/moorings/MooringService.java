package com.nautik.api.service.moorings;


import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.exceptions.ForbiddenException;
import com.nautik.api.domain.exceptions.MooringHasBookingsException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.MooringDimension;
import com.nautik.api.domain.moorings.MooringIncident;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.MooringIncidentDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.dto.mooring.create.MooringDimensionCreateDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;
import com.nautik.api.repository.moorings.MooringIncidentRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.common.util.count.report.qual.ReportCreation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MooringService {

    public final MooringRepository mooringRepository;
    public final MooringCategoryRepository mooringCategoryRepository;
    public final ModelMapper modelMapper;
    public final MooringDimensionRepository dimensionRepository;
    public final ZoneRepository zoneRepository;
    private final PortRepository portRepository;
    private final MooringIncidentRepository mooringIncidentRepository;

    public List<MooringDto> findAll() {
        return mooringRepository.findAll().stream().map(mooring -> modelMapper.map(mooring, MooringDto.class)).toList();
    }

    public MooringDto findById(Integer mooringId) {
        return modelMapper.map(mooringRepository.findById(mooringId), MooringDto.class);
    }


    public List<MooringDto> findByMooringCategoryId(Integer mooringCategoryId) {

        List<Mooring> moorings = mooringRepository.findByMooringCategoryId(mooringCategoryId);
        return moorings.stream().map(m -> modelMapper.map(m, MooringDto.class)).toList();
    }

    public MooringDimensionDto createMooringDimension(MooringDimensionCreateDto mooringDimensionDto) {
        MooringDimension mooringDimension = dimensionRepository.save(modelMapper.map(mooringDimensionDto, MooringDimension.class));
        return modelMapper.map(mooringDimension, MooringDimensionDto.class);
    }


    public List<MooringDto> findAllByPortName(String portName) {
        String port = portName.replace("_", " ");
        return mooringRepository.findAllByMooringCategory_Zone_Port_NameIgnoreCase(port)
                .stream()
                .map(mooring -> modelMapper.map(mooring, MooringDto.class))
                .toList();
    }


    public List<MooringDto> findAllByPortId(Integer portId) {
        return mooringRepository.findAllByMooringCategoryZonePortId(portId)
                .stream()
                .map(mooring -> modelMapper.map(mooring, MooringDto.class))
                .toList();
    }

    public MooringDto createMooring(Integer mooringCategoryId, CreateMooringDto mooringDto) {
        MooringCategory mooringCategory = mooringCategoryRepository.findById(mooringCategoryId).orElseThrow(() -> new EntityNotFoundException("Mooring category not found"));
        Mooring mooring = modelMapper.map(mooringDto, Mooring.class);
        mooring.setMooringCategory(mooringCategory);
        return modelMapper.map(mooringRepository.save(mooring), MooringDto.class);
    }


    /*
        public MooringDto createMooring(Integer mooringCategoryId, CreateMooringDto dto) {
            MooringCategory mooringCategory = findOrCreateCategory(dto);
            Mooring mooring = new Mooring();
            mooring.setMooringCategory(mooringCategory);
            mooring.setNumber(dto.getNumber());
            return modelMapper.map(mooringRepository.save(mooring), MooringDto.class);

        }
    /*
        public MooringCategory findOrCreateCategory(CreateMooringDto dto) {
            return (MooringCategory) mooringCategoryRepository
                    .findByDimensions_IdAndZone_Id(Long.valueOf(dto.getDimensionsId()), dto.getZoneId())
                    .orElseGet(() -> {
                        MooringCategory mooringCategory1 = new MooringCategory();
                        mooringCategory1.setZone(zoneRepository.findZoneById(Math.toIntExact(dto.getZoneId())).orElseThrow());
                        mooringCategory1.setDimensions(dimensionRepository.findById(dto.getDimensionsId()).orElseThrow());
                        return mooringCategoryRepository.save(mooringCategory1);
                    });
        }
    */
    public void delete(Integer id) {
        Mooring mooring = mooringRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mooring not found"));
        try{
            mooringRepository.delete(mooring);
        }catch (Exception hex){
            throw new MooringHasBookingsException();
        }

    }

    public MooringDto update(Integer id, CreateMooringDto dto) {
        Mooring mooring = mooringRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mooring not found"));
        Mooring providedMooring = modelMapper.map(dto, Mooring.class);
        providedMooring.setId(mooring.getId());
        providedMooring.setMooringCategory(mooring.getMooringCategory());
        return modelMapper.map(mooringRepository.save(providedMooring), MooringDto.class);
    }

    public List<MooringDto> findAllByZoneId(long zoneId) {
        return mooringRepository.findAllByMooringCategory_Zone_Id(zoneId).stream()
                .map(mooring -> modelMapper.map(mooring, MooringDto.class))
                .toList();
    }



    public List<MooringDimensionDto> getAllMooringsDimensions() {
        return dimensionRepository.findAll()
                .stream()
                .map((element) -> modelMapper
                        .map(element, MooringDimensionDto.class))
                .toList();
    }

    public MooringCategoryDto findCategoryById(int zoneId, int dimensionsId) {
        return modelMapper.map(
                mooringCategoryRepository
                        .findByDimensions_IdAndZone_Id((long) dimensionsId, zoneId), MooringCategoryDto.class);
    }

    public MooringIncidentDto createMooringIncident(Integer portId, MooringIncidentDto mooringIncidentDto,Integer mooringId){
        validateOwnerShip(portId, mooringId);
        Mooring mooring = mooringRepository.findById(mooringId)
                .orElseThrow(()->new EntityNotFoundException("Mooring not found")
        );
        MooringIncident providedMooringIncident = modelMapper.map(mooringIncidentDto, MooringIncident.class);

        return modelMapper.map(mooringIncidentRepository.save(providedMooringIncident), MooringIncidentDto.class);
    }

    public List<MooringIncidentDto> getCurrentMooringIncidents(Integer portId){
        Date date  = new Date();
        List<MooringIncident> mooringIncidents = mooringIncidentRepository.findCurrentIncidents(date,portId);

        return mooringIncidents.stream()
                .map(m->modelMapper.map(m, MooringIncidentDto.class));
    }

    public List<MooringIncidentDto> getAllMooringIncidents(Integer portId){
        Date date  = new Date();
        List<MooringIncident> mooringIncidents = mooringIncidentRepository.findCurrentIncidents(date,portId);

        return mooringIncidents.stream()
                .map(m->modelMapper.map(m, MooringIncidentDto.class));
    }



    public void validateOwnerShip(Integer portId, Integer mooringId){
        Mooring mooring = mooringRepository.findById(mooringId)
                .orElseThrow(()->new EntityNotFoundException("Mooring not found")
                );


       if (!Objects.equals(mooring.getMooringCategory().getZone().getPort().getId(), portId))
       {
        throw new ForbiddenException("You dont have access");
       }
    }

}
