package com.nautik.api.service.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.repository.bookings.BookingRepository;
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






    public List<MooringCategoryDto> getAllMooringCategoriesDtoByPort(Integer portId) {

        return mooringCategoryRepository

                .findByZonePortIdAndDimensionsMaxBeamGreaterThanAndDimensionsMaxLengthGreaterThan(portId, 5L, 2L).stream().map(mc -> modelMapper.map(mc, MooringCategoryDto.class)).toList();

    }


    public List<MooringCategory> getAllMooringCategoriesByPort(Integer portId) {
        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByZonePortId(portId);

        if (mooringCategories.isEmpty()) {
            throw new ResourceNotFoundException("No mooring categories found");
        }

        return mooringCategories;
    }


    public List<MooringCategoryDto> getAllMooringCategoriesByPortAndPriceAndStartDateAndEndDate(Integer portId, String stringStartDate, String endStartDate) throws ParseException {


        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(endStartDate);
        if (startDate.after(endDate)) {
            //throw exception
        }
        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByZonePortId(portId);

        List<MooringCategory> mooringCategoriesWithMinPrice = mooringCategories.stream().map(mc -> getMooringCategoryWithMinPrice(mc, startDate, endDate)).toList();

        return mooringCategoriesWithMinPrice.stream().map(mc -> modelMapper.map(mc, MooringCategoryDto.class)).toList();
    }


    public List<MooringCategoryDto> getAllMooringCategoriesByPortDimensionsAndAvailability(Integer portId, Integer length, Integer beam, String stringStartDate, String strindEndDate) {

        Date startDate =  dateFormater(stringStartDate);
        Date endDate = dateFormater(strindEndDate);

        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByDimensionsMaxBeamGreaterThanEqualAndDimensionsMaxLengthGreaterThanEqualAndZonePortId(beam,length,portId);
        List<Booking> bookings = bookingService.getBookingsByMooringCategoryAndAvailability(mooringCategories,startDate,endDate);


        return new ArrayList<MooringCategoryDto>();
    }


    private MooringCategory getMooringCategoryWithMinPrice(MooringCategory mooringCategory, Date startDate, Date endDate) {

        Predicate<PriceConfiguration> priceConfStartDateFilter = (PriceConfiguration pc) -> pc.getStartDate().before(endDate);
        Predicate<PriceConfiguration> priceConfEndDateFilter = (PriceConfiguration pc) -> pc.getEndDate().after(startDate);
        Predicate<PriceConfiguration> priceConfigurationDateFilter = priceConfStartDateFilter.and(priceConfEndDateFilter);

        if (mooringCategory.getPriceConfigurations().isEmpty()) {
            //Throw new Exception
        }

        List<PriceConfiguration> filteredPriceConfigurations = mooringCategory.getPriceConfigurations().stream().filter(priceConfigurationDateFilter).toList();


        PriceConfiguration priceConfiguration = filteredPriceConfigurations.get(0);

        mooringCategory.setMinPrice(priceConfiguration.getMinPrice());

        return mooringCategory;
    }


    private Date dateFormater(String dateString)  {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try
        {
            return formatter.parse(dateString);
        } catch (ParseException ignore){
        }
        return new Date();

    }

}
