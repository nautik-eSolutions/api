package com.nautik.api.service.moorings;


import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.MooringMooringStatus;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.mooring.create.CreateMooringDto;
import com.nautik.api.repository.location.ZoneRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringDimensionRepository;
import com.nautik.api.repository.moorings.MooringMooringStatusRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MooringService {

    public final MooringRepository mooringRepository;
    public final MooringCategoryRepository mooringCategoryRepository;
    public final ModelMapper modelMapper;
    public final MooringMooringStatusRepository statusRepository;
    public final MooringDimensionRepository dimensionRepository;
    public final ZoneRepository zoneRepository;

    public List<MooringDto> findAll(){
        return mooringRepository.findAll().stream().map(mooring -> modelMapper.map(mooring, MooringDto.class)).toList();
    }

    public MooringDto findById(long mooringId){
        return modelMapper.map(mooringRepository.findById(mooringId), MooringDto.class);
    }

    public List<MooringDto> findAllByPort(String portName){
        String port = portName.replace("_"," ");
        return mooringRepository.findAllByMooringCategory_Zone_Port_NameIgnoreCase(port)
                .stream()
                .map(mooring -> modelMapper.map(mooring, MooringDto.class))
                .toList();
    }
    public MooringDto createMooring(Long portId, CreateMooringDto dto){
        MooringCategory mooringCategory = findOrCreateCategory(dto);
        Mooring mooring = new Mooring();
        mooring.setMooringCategory(mooringCategory);
        mooring.setNumber(dto.getNumber());
        return modelMapper.map(mooringRepository.save(mooring), MooringDto.class);

    }

    public MooringCategory findOrCreateCategory(CreateMooringDto dto){
        return (MooringCategory) mooringCategoryRepository
                .findByDimensions_IdAndZone_Id(Long.valueOf(dto.getDimensionsId()), dto.getZoneId())
                .orElseGet(() ->{
                    MooringCategory mooringCategory1 = new MooringCategory();
                    mooringCategory1.setZone(zoneRepository.findZoneById(Math.toIntExact(dto.getZoneId())).orElseThrow());
                    mooringCategory1.setDimensions(dimensionRepository.findById(Long.valueOf(dto.getDimensionsId())).orElseThrow());
                    return mooringCategoryRepository.save(mooringCategory1);
                });
    }
    public void delete(long id){
        Mooring mooring = mooringRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Mooring not found"));
        mooringRepository.delete(mooring);
    }

    public MooringDto update(long id, MooringDto dto){
        Mooring mooring = mooringRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Mooring not found"));
        Mooring providedMooring = modelMapper.map(dto, Mooring.class);
        providedMooring.setId(mooring.getId());
        return modelMapper.map(mooringRepository.save(providedMooring), MooringDto.class);
    }

    public List<MooringDto> findAllByZoneId(long zoneId){
        return mooringRepository.findAllByMooringCategory_Zone_Id(zoneId).stream()
                .map(mooring -> modelMapper.map(mooring, MooringDto.class))
                .toList();
    }

    public List<MooringDto> findAllByZoneAvailable(long zoneId){
        List<MooringDto> mooringsZone = this.findAllByZoneId(zoneId);
        List<MooringDto> available = new ArrayList<>();

        mooringsZone.forEach(mooringDto -> {
            MooringMooringStatus status = statusRepository.findFirstByMooring_Id(mooringDto.getId());

            if(status.getMooringStatus().getId() == 1){
                available.add(mooringDto);
            }

        });
        return  available;
    }

    public List<MooringDimensionDto> getAllMooringsDimensions(){
        return dimensionRepository.findAll()
                .stream()
                .map((element) -> modelMapper
                        .map(element, MooringDimensionDto.class))
                .toList();
    }

    public MooringCategoryDto findCategoryById(int zoneId, int dimensionsId){
        return modelMapper.map(
                mooringCategoryRepository
                        .findByDimensions_IdAndZone_Id((long) dimensionsId, zoneId), MooringCategoryDto.class);
    }


}
