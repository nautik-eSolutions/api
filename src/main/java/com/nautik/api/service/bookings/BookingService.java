package com.nautik.api.service.bookings;


import com.nautik.api.domain.Boat;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.booking.CheckInOut;
import com.nautik.api.domain.exceptions.BoatAlreadyInPort;
import com.nautik.api.domain.exceptions.NoAvailabilityException;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.dto.bookings.BookingRequestDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.repository.boat.BoatRepository;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.bookings.CheckInOutRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final MooringRepository mooringRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final BoatRepository boatRepository;
    private final PortRepository portRepository;
    private final CheckInOutRepository checkInOutRepository;


    public List<BookingOccupancyDto> getAllBookingsByPortFromNow(Integer portId){
        Date startDate = new Date();

        //List<Booking> getBookings = bookingRepository.findAllByMooringMooringCategoryZonePortIdAndStartDateAfter(portId, startDate);

        List<Booking> getBookings = bookingRepository.findAllByMooringMooringCategoryZonePortId(portId);
        return getBookings.stream().map(b->modelMapper.map(b, BookingOccupancyDto.class)).toList();
    }


    public Booking createBooking(Integer mooringCategoryId ,Boat boat,Date startDate, Date endDate){
        //falta implementar status, a decidir si sera con registro o sin
        List<Mooring> availableMoorings =  mooringRepository.findMooringsByMooringCategory(mooringCategoryId);

        if (availableMoorings.isEmpty()){
            throw new NoAvailabilityException();
        }

        List<Booking> bookings = bookingRepository.findByMooringCategoryAndStartDateAndStatusConfirmed
                (mooringCategoryId,startDate,endDate);



        Booking newBooking =  new Booking(startDate,endDate);

        Mooring assignedMooring =  IntervalPartitionService.assignMooring
                (newBooking,bookings,availableMoorings);

        if (assignedMooring == null){
            throw new NoAvailabilityException();
        }


        Double totalCost = (double) Math.round(getPriceForBooking(assignedMooring,startDate,endDate));


        newBooking.setMooring(assignedMooring);
        newBooking.setBoat(boat);
        newBooking.setTotalCost(totalCost);




        return newBooking;
    }

    public boolean isBoatInPortByMooringCategory(Integer mooringCategoryId, Integer boatId, Date startDate, Date endDate){
        Port port = portRepository
                .findByMooringCategoryId(mooringCategoryId)
                .orElseThrow(
                        ()->new EntityNotFoundException("Port not found")
                );

        HashMap<Integer, Boat> boatsInPort = (HashMap<Integer, Boat>) boatRepository
                .getAllBoatsInPortBetweenDates(port.getId(), startDate, endDate)
                .stream().collect(Collectors.toMap(Boat::getId, boat -> boat));

        if (boatsInPort.containsKey(boatId)){
            throw new BoatAlreadyInPort();
        }
        return false;
    }


    protected Double getPriceForBooking(Mooring mooring, Date startDate, Date endDate) {
        MooringCategory  mooringCategory = mooring.getMooringCategory();
        Double totalPrice = mooringCategory.getMinPricePerDay();
        double iva = 1.21;

        //Query bd quitar predicates
        Predicate<PriceConfiguration> priceConfStartDateFilter = (PriceConfiguration pc) -> pc.getStartDate().before(endDate);
        Predicate<PriceConfiguration> priceConfEndDateFilter = (PriceConfiguration pc) -> pc.getEndDate().after(startDate);
        Predicate<PriceConfiguration> priceConfigurationDateFilter = priceConfStartDateFilter.and(priceConfEndDateFilter);

        List<PriceConfiguration> filteredPriceConfigurations = mooringCategory.getPriceConfigurations().stream().filter(priceConfigurationDateFilter).toList();

        int days = getDaysBetweenDates(startDate, endDate);
        if (filteredPriceConfigurations.isEmpty()) {
            return ((totalPrice * getMultiplyer(mooringCategory, startDate, endDate)) * days) * iva ;
        }


        PriceConfiguration priceConfiguration = filteredPriceConfigurations.get(0);

        return ((priceConfiguration.getMinPricePerDay() * getMultiplyer(mooringCategory, startDate, endDate)) * days ) * iva ;

    }

    private int getDaysBetweenDates(Date startDate, Date endDate) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }


    public List<BookingDto> getAllBookingsByMooringId(Integer mooringId){
        List <Booking> bookings = bookingRepository.findAllByMooringId(mooringId);
        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings we're found");
        }

        return bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).toList();
    }
    public List<BookingDto> getBookingsByMooringDimensionsAndAvailability(Integer beam , Integer length, String stringStartDate, String stringEndDate){

        Date startDate =  dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<Booking> bookings = bookingRepository.findAllByMooringMooringCategoryDimensionsMaxLengthGreaterThanEqualAndMooringMooringCategoryDimensionsMaxBeamGreaterThanEqualAndStartDateBeforeAndEndDateAfter(length,beam,endDate,startDate);

        return bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).toList();
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

    public void saveBookingAfterSuccessPayment(Booking booking){
        CheckInOut checkInOut = new CheckInOut();
        Date startDate =booking.getStartDate();
        Date endDate = booking.getEndDate();

        String arrivalTime = "15:00";
        String departureTime = "13:00";

        checkInOut.setScheduledCheckinTime(addTimeToDate(startDate,arrivalTime));
        checkInOut.setScheduledCheckoutTime(addTimeToDate(endDate,departureTime));
        checkInOut.setBooking(booking);

        bookingRepository.save(booking);
        checkInOutRepository.save(checkInOut);
    }

    private Date addTimeToDate(Date date, String hoursAndMinutes){
        String[] parts = hoursAndMinutes.split(":");
        int hoursToAdd = Integer.parseInt(parts[0]);
        int minutesToAdd = Integer.parseInt(parts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        calendar.add(Calendar.MINUTE, minutesToAdd);

        return calendar.getTime();
    }

    private Date dateFormater(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatter.parse(dateString);
        } catch (ParseException ignored) {

        }


        return new Date();
    }










}
