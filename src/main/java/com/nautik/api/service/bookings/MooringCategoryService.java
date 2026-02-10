package com.nautik.api.service.bookings;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MooringCategoryService {


    private final MooringCategoryRepository mooringCategoryRepository;

    private final PriceConfigurationRepository priceConfigurationRepository;
    private final ModelMapper modelMapper;

    public List<MooringCategoryDto> getAllMooringCategoriesByPort(Integer portId){

        return mooringCategoryRepository

                .findByZonePortIdAndDimensionsMaxBeamGreaterThanAndDimensionsMaxLengthGreaterThan(portId,5L,2L)
                .stream()
                .map(mc -> modelMapper.map(mc, MooringCategoryDto.class)).toList();

    }


    public List<PriceConfigurationDto> getPriceConfigurations(Integer portId){
        List<MooringCategoryDto> mooringCategories = mooringCategoryRepository
                .findByZone_Port_Id(portId).stream().map(mc->modelMapper.map(mc, MooringCategoryDto.class)).toList();

        List<PriceConfiguration> priceConfigurations = new ArrayList<>();

        mooringCategories.forEach(mc-> priceConfigurations.addAll(priceConfigurationRepository.findByMooringCategoriesId(mc.getId())));


        return priceConfigurations.stream().map(pc->modelMapper.map(pc, PriceConfigurationDto.class)).toList();
    }











}
