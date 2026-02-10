package com.nautik.api.service.bookings;

import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.PriceConfigurationDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;


@Service
@RequiredArgsConstructor
public class MooringCategoryService {


    private final MooringCategoryRepository mooringCategoryRepository;

    private final PriceConfigurationRepository priceConfigurationRepository;

    private final MooringRepository mooringRepository;

    private final ModelMapper modelMapper;

    public List<MooringCategoryDto> getAllMooringCategoriesDtoByPort(Integer portId) {

        return mooringCategoryRepository

                .findByZonePortIdAndDimensionsMaxBeamGreaterThanAndDimensionsMaxLengthGreaterThan(portId, 5L, 2L)
                .stream()
                .map(mc -> modelMapper.map(mc, MooringCategoryDto.class)).toList();

    }


    public PriceConfigurationDto getPriceConfigurationsDtoByPort(Integer portId) throws ParseException {
        List<MooringCategoryDto> mooringCategories = mooringCategoryRepository
                .findByZone_Port_Id(portId).stream().map(mc -> modelMapper.map(mc, MooringCategoryDto.class)).toList();

        List<PriceConfiguration> priceConfigurations = new ArrayList<>();

        mooringCategories.forEach(mc -> priceConfigurations.addAll(priceConfigurationRepository.findByMooringCategoriesId(mc.getId())));

        // priceConfigurations.stream().map(pc -> modelMapper.map(pc, PriceConfigurationDto.class)).toList();

        Date startDate = dateFormater("2026-05-24");
        Date endDate = dateFormater("2026-06-10");

        getAllMooringCategoriesByPortAndPrice(1,startDate,endDate);

        return modelMapper.map(priceConfigurationRepository.findByMooringCategoryAndDates(1), PriceConfigurationDto.class);
    }


    public List<MooringCategory> getAllMooringCategoriesByPort(Integer portId) {
        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByZonePortId(portId);

        if (mooringCategories.isEmpty()) {
            throw new ResourceNotFoundException("No mooring categories found");
        }

        return mooringCategories;
    }


    public List<MooringCategory> getAllMooringCategoriesByPortAndPrice(Integer portId, Date startDate, Date endDate) {

        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByZonePortId(portId);

        if (mooringCategories.isEmpty()) {
            throw new ResourceNotFoundException("No mooring categories found");
        }

        Predicate<PriceConfiguration> priceConfStartDateFilter = (PriceConfiguration pc) -> pc.getStartDate().before(startDate);
        Predicate<PriceConfiguration> priceConfEndDateFilter = (PriceConfiguration pc) -> pc.getEndDate().after(endDate);
        Predicate<PriceConfiguration> priceConfigurationDateFilter = priceConfStartDateFilter.and(priceConfEndDateFilter);

        List <MooringCategory> filterMooringCategories =  mooringCategories.stream().filter(mc-> mc.getPriceConfigurations().stream().filter(priceConfigurationDateFilter).isParallel()).toList();

        filterMooringCategories.forEach(System.out::println);

        return mooringCategories;
    }




    private Date dateFormater(String dateString) throws ParseException {
        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(dateString);
    }

}
