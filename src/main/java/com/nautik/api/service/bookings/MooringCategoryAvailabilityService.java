package com.nautik.api.service.bookings;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.exceptions.NoAvailabilityException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.mooring.MooringCategoryAvailabilityDto;
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
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MooringCategoryAvailabilityService {


    private final MooringCategoryRepository mooringCategoryRepository;

    private final PriceConfigurationRepository priceConfigurationRepository;

    private final MooringRepository mooringRepository;

    private final BookingService bookingService;

    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;


    public List<Mooring> getMooringCategoriesByAvailabilityPortAndAvailability(Integer portId, Double length, Double beam, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<MooringCategory> mooringCategories = getMooringCategoriesByPortAndDimensions(portId, length, beam);


        return new ArrayList<>();
    }

    public List<MooringCategoryAvailabilityDto> getMooringCategoriesWithByAvailability(Integer portId, Double length, Double beam, Double draft, String stringStartDate, String stringEndDate) {

        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<MooringCategory> mooringCategories = mooringCategoryRepository.getAllByDimensionsAndAvailability(portId, length,beam,draft,startDate,endDate);
        List<MooringCategory> availableMooringCategories = new ArrayList<>();

        for (MooringCategory category : mooringCategories) {
            if (mooringCategoryIsAvailable(category, startDate, endDate)) {
                availableMooringCategories.add(category);
            }
        }

        if (availableMooringCategories.isEmpty()) {
            throw new NoAvailabilityException();
        }

        List<MooringCategory> pricedCategories = availableMooringCategories.stream()
                .map(mc -> setPriceInMooringCategory(mc, startDate, endDate))
                .toList();

        return pricedCategories.stream()
                .map(mc -> setPriceInMooringCategoryDto(mc, stringStartDate, stringEndDate))
                .collect(Collectors.toList());


    }

    private boolean mooringCategoryIsAvailable(MooringCategory mooringCategory, Date startDate, Date endDate){

        List<Mooring> mooringsInMooringCategory =
                mooringRepository.findByMooringCategoryId(mooringCategory.getId());
        System.out.println("Ingeniero hay que validar el status de mooring");

        if (mooringsInMooringCategory.isEmpty()){
            throw new NoAvailabilityException();
        }

        List<Booking> existingBookings = bookingRepository.
                findByMooringCategoryAndStartDateAndStatusConfirmed(mooringCategory.getId(),startDate,endDate );

        Booking testBooking = new Booking();

        Mooring assignedMooring = IntervalPartitionService.assignMooring(
                testBooking,
                existingBookings,
                mooringsInMooringCategory
        );


        return assignedMooring != null;
    }




    public List<MooringCategoryAvailabilityDto> getMooringCategoriesbyPortDimensionsAndAvailability(Integer portId, Double length, Double beam, Double draft, String stringStartDate, String stringEndDate

    ) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);


        List<MooringCategory> mooringCategories = mooringCategoryRepository.getAllByDimensionsAndAvailability(portId, length, beam, draft, startDate, endDate);

        List<MooringCategory> mooringCategoriesWithMinPrice = mooringCategories.stream().map(mc -> setPriceInMooringCategory(mc, startDate, endDate)).toList();


        return mooringCategoriesWithMinPrice.stream().map(mc -> setPriceInMooringCategoryDto(mc, stringStartDate, stringEndDate)).toList();

    }

    public MooringCategoryAvailabilityDto getMooringCategoryByIdAndAvailability(Integer mooringCategoryId, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        MooringCategory mooringCategory = mooringCategoryRepository.getMooringCategoryByAvailability(mooringCategoryId, startDate, endDate).orElseThrow(NoAvailabilityException::new);


        MooringCategory pricedMooringCategory = setPriceInMooringCategory(mooringCategory, startDate, endDate);

        return setPriceInMooringCategoryDto(pricedMooringCategory, stringStartDate, stringEndDate);
    }

    private double getMultiplyer(MooringCategory mooringCategory, Date startDate, Date endDate) {
        int availableMoorings = mooringRepository.findNumberOfFreeMooringsByCategory(mooringCategory.getId(), startDate, endDate);
        int totalMoorings = mooringRepository.findNumberMooringsByCategory(mooringCategory.getId());

        if (availableMoorings == 0 && totalMoorings == 0) {
            return 0;
        }
        double occupancyRate = (double) (totalMoorings - availableMoorings) / totalMoorings;

        return 1.0 + (occupancyRate * 0.4);
    }


    private List<MooringCategory> getMooringCategoriesByPortAndDimensions(Integer portId, Double length, Double beam) {

        List<MooringCategory> mooringCategories = mooringCategoryRepository.findAllByZonePortIdAndDimensionsMaxLengthGreaterThanEqualAndDimensionsMaxBeamGreaterThanEqual(portId, length, beam);


        if (mooringCategories.isEmpty()) {

            //throw new error
        }

        return mooringCategories;
    }


    protected MooringCategory setPriceInMooringCategory(MooringCategory mooringCategory, Date startDate, Date endDate) {

        Predicate<PriceConfiguration> priceConfStartDateFilter = (PriceConfiguration pc) -> pc.getStartDate().before(endDate);
        Predicate<PriceConfiguration> priceConfEndDateFilter = (PriceConfiguration pc) -> pc.getEndDate().after(startDate);
        Predicate<PriceConfiguration> priceConfigurationDateFilter = priceConfStartDateFilter.and(priceConfEndDateFilter);

        List<PriceConfiguration> filteredPriceConfigurations = mooringCategory.getPriceConfigurations().stream().filter(priceConfigurationDateFilter).toList();


        if (filteredPriceConfigurations.isEmpty()) {
            mooringCategory.setMinPricePerDay(
                    mooringCategory.getMinPricePerDay() * getMultiplyer(mooringCategory, startDate, endDate));
            return mooringCategory;
        }


        PriceConfiguration priceConfiguration = filteredPriceConfigurations.get(0);

        mooringCategory.setMinPricePerDay(priceConfiguration.getMinPricePerDay() * getMultiplyer(mooringCategory, startDate, endDate));

        return mooringCategory;
    }

    private MooringCategoryAvailabilityDto setPriceInMooringCategoryDto(MooringCategory pricedMooringCategory, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);


        int days = getDaysBetweenDates(startDate, endDate);
        double totalPrice = pricedMooringCategory.getMinPricePerDay() * days;

        MooringCategoryAvailabilityDto mooringCategoryAvailabilityDto = modelMapper.map(pricedMooringCategory, MooringCategoryAvailabilityDto.class);
        mooringCategoryAvailabilityDto.setStartDate(stringStartDate);
        mooringCategoryAvailabilityDto.setEndDate(stringEndDate);
        mooringCategoryAvailabilityDto.setMinPricePerDay((totalPrice * 1.21) / days);
        mooringCategoryAvailabilityDto.setBasePrice(totalPrice);
        mooringCategoryAvailabilityDto.setTax(totalPrice * 0.21);
        mooringCategoryAvailabilityDto.setTotalPrice(totalPrice * 1.21);
        return mooringCategoryAvailabilityDto;
    }

    private int getDaysBetweenDates(Date startDate, Date endDate) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
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
