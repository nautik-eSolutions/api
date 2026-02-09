package com.nautik.api.service.bookings;

import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MooringCategoryService {


    private final MooringCategoryRepository mooringCategoryRepository;
    private final ModelMapper modelMapper;

    public List<MooringCategoryDto> getAllMooringCategoriesByPort(Integer portId){
        return mooringCategoryRepository
                .findAllByZone_Port_Id(portId)
                .stream()
                .map(MooringCategoryDto::new).toList();

    }






}
