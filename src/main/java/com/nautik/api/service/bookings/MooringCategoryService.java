package com.nautik.api.service.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.exceptions.NoAvailabilityException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.moorings.PriceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


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

    private final BookingService bookingService;

    private final ModelMapper modelMapper;


    public List<Mooring> getMooringCategoriesByAvailabilityPortAndAvailability(Integer portId, Integer length, Integer beam, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<MooringCategory> mooringCategories = getMooringCategoriesByPortAndDimensions(portId, length, beam);




        return new ArrayList<>();
    }



    public List<MooringCategoryDto> getMooringCategoriesbyPortDimensionsAndAvailability(
            Integer portId, Integer length, Integer beam, String stringStartDate, String stringEndDate
    ){

        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);


        List<MooringCategory> mooringCategories = mooringCategoryRepository.
                getAllByDimensionsAndAvailability(portId,length,beam,startDate,endDate);

        List<MooringCategory> mooringCategoriesWithMinPrice = mooringCategories.stream().map(mc -> setPriceInMooringCategory(mc, startDate, endDate)).toList();


        return mooringCategoriesWithMinPrice
                .stream().map(mc ->modelMapper.map(mc, MooringCategoryDto.class)).toList();

    }

    public MooringCategoryDto getMooringCategoryByIdAndAvailability(Integer mooringCategoryId, String stringStartDate, String stringEndDate){
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        MooringCategory mooringCategory = mooringCategoryRepository.getMooringCategoryByAvailability(mooringCategoryId,startDate,endDate)
                .orElseThrow(NoAvailabilityException::new);


        MooringCategory pricedMooringCategory = setPriceInMooringCategory(mooringCategory,startDate,endDate);

        return modelMapper.map(pricedMooringCategory, MooringCategoryDto.class);
    }





    private List<MooringCategory> getMooringCategoriesByPortAndDimensions(Integer portId, Integer length, Integer beam) {

        List<MooringCategory> mooringCategories =
                mooringCategoryRepository.findAllByZonePortIdAndDimensionsMaxLengthGreaterThanEqualAndDimensionsMaxBeamGreaterThanEqual(portId, length, beam);


        if (mooringCategories.isEmpty()) {

            //throw new error
        }

        return mooringCategories;
    }


    public List<MooringCategoryDto> getMooringCategoriesPriceWithDatesAndPort(Integer portId, String stringStartDate, String endStartDate) {


        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(endStartDate);
        if (startDate.after(endDate)) {
            //throw exception
        }
        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByZonePortId(portId);

        List<MooringCategory> mooringCategoriesWithMinPrice = mooringCategories.stream().map(mc -> setPriceInMooringCategory(mc, startDate, endDate)).toList();

        return mooringCategoriesWithMinPrice.stream().map(mc -> modelMapper.map(mc, MooringCategoryDto.class)).toList();
    }


    public List<MooringCategoryDto> getAllMooringCategoriesByPortDimensionsAndAvailability(Integer portId, Integer length, Integer beam, String stringStartDate, String stringEndDate) {

        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByDimensionsMaxBeamGreaterThanEqualAndDimensionsMaxLengthGreaterThanEqualAndZonePortId(beam, length, portId);
        List<Booking> bookings = bookingService.getBookingsByMooringCategoriesAndAvailability(mooringCategories, startDate, endDate);


        return new ArrayList<MooringCategoryDto>();
    }




    private MooringCategory setPriceInMooringCategory(MooringCategory mooringCategory, Date startDate, Date endDate) {

        Predicate<PriceConfiguration> priceConfStartDateFilter = (PriceConfiguration pc) -> pc.getStartDate().before(endDate);
        Predicate<PriceConfiguration> priceConfEndDateFilter = (PriceConfiguration pc) -> pc.getEndDate().after(startDate);
        Predicate<PriceConfiguration> priceConfigurationDateFilter = priceConfStartDateFilter.and(priceConfEndDateFilter);

        if (mooringCategory.getPriceConfigurations().isEmpty()) {
            mooringCategory.setMinPrice(200);
            return mooringCategory;

        }

        List<PriceConfiguration> filteredPriceConfigurations = mooringCategory.getPriceConfigurations().stream().filter(priceConfigurationDateFilter).toList();


        if (filteredPriceConfigurations.isEmpty()){
            mooringCategory.setMinPrice(200);
            return mooringCategory;
        }


        PriceConfiguration priceConfiguration = filteredPriceConfigurations.get(0);

        mooringCategory.setMinPrice(priceConfiguration.getMinPrice());

        return mooringCategory;
    }


    private Date dateFormater(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatter.parse(dateString);
        } catch (ParseException ignore) {
        }
        return new Date();

    }

}
