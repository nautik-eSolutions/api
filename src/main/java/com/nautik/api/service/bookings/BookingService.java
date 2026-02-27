package com.nautik.api.service.bookings;


import com.nautik.api.domain.Boat;
import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.exceptions.NoAvailabilityException;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.dto.bookings.BookingRequestDto;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final MooringRepository mooringRepository;
    private final MooringCategoryRepository mooringCategoryRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    public List<BookingDto> getAvailableMooringCategoriesByPortAndStartDateAndEndDate(Integer mooringCategoryId, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<Booking> bookingsWithinSelectedDates = bookingRepository.findAllByMooringMooringCategoryIdAndStartDateBeforeAndEndDateAfter(mooringCategoryId, endDate, startDate);


        List<Mooring> mooringsOcuppied = mooringRepository.findAllByBookingsIn(bookingsWithinSelectedDates);


        return bookingsWithinSelectedDates.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).toList();
    }

    public List<BookingOccupancyDto> getAllBookingsByPortFromNow(Integer portId){
        Date startDate = new Date();

        //List<Booking> getBookings = bookingRepository.findAllByMooringMooringCategoryZonePortIdAndStartDateAfter(portId, startDate);

        List<Booking> getBookings = bookingRepository.findAllByMooringMooringCategoryZonePortId(portId);
        return getBookings.stream().map(b->modelMapper.map(b, BookingOccupancyDto.class)).toList();
    }



    public List<MooringDto> getAllFreeMooringsByDateAndCategory(Integer mooringCategoryId, String stringStartDate, String stringEndDate){
        Date startDate = dateFormater(stringStartDate);
        Date endDate =  dateFormater(stringEndDate);


        return mooringRepository
                .findFreeMooringsByCategory(mooringCategoryId,startDate,endDate)
                .stream()
                .map(m->modelMapper.map(m, MooringDto.class)).toList();

    }


    public Boolean createBooking(BookingRequestDto bookingRequestDto, Integer userId){
        Date startDate = dateFormater(bookingRequestDto.getStartDate());
        Date endDate =  dateFormater(bookingRequestDto.getEndDate());
        User user = userRepository.findById(userId).orElseThrow();
        Boat boat = user
                    .getBoats()
                    .stream()
                    .filter(b-> Objects.equals(b.getId(), bookingRequestDto.getBoatId()))
                .toList().get(0);

        Integer mooringCategoryId =  bookingRequestDto.getMooringCategoryId();


        //falta implementar status, a decidir si sera con registro o sin
        List<Mooring> availableMoorings =  mooringRepository.findMooringsByMooringCategory(mooringCategoryId);



        if (availableMoorings.isEmpty()){
            throw new NoAvailabilityException();
        }

        List<Booking> bookings = bookingRepository.findByMooringCategoryAndStartDateAndStatusConfirmed
                (mooringCategoryId,startDate,endDate);


        Double totalCost = 435.00D;

        Booking newBooking =  new Booking(startDate,endDate);

        Mooring assignedMooring =  IntervalPartitionService.assignMooring
                (newBooking,bookings,availableMoorings);

        if (assignedMooring == null){
            throw new NoAvailabilityException();
        }

        newBooking.setMooring(assignedMooring);
        newBooking.setBoat(boat);
        newBooking.setTotalCost(totalCost);

        bookingRepository.save(newBooking);


        return true;
    }

    public List<BookingDto> getAllBookingsByMooringId(Integer mooringId){
        List <Booking> bookings = bookingRepository.findAllByMooringId(mooringId);
        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings we're found");
        }

        return bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).toList();
    }


    private Date dateFormater(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatter.parse(dateString);
        } catch (ParseException ignored) {

        }


        return new Date();
    }


    public List<Booking> getBookingsByMooringCategoriesAndAvailability(Integer mooringCategoryId, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        return bookingRepository.findAllByMooringMooringCategoryIdAndStartDateBeforeAndEndDateAfter(mooringCategoryId, endDate, startDate);
    }


    public List<BookingDto> getBookingsByMooringDimensionsAndAvailability(Integer beam , Integer length, String stringStartDate, String stringEndDate){

        Date startDate =  dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);

        List<Booking> bookings = bookingRepository.findAllByMooringMooringCategoryDimensionsMaxLengthGreaterThanEqualAndMooringMooringCategoryDimensionsMaxBeamGreaterThanEqualAndStartDateBeforeAndEndDateAfter(length,beam,endDate,startDate);

        return bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).toList();
    }


    public List<Booking> getBookingsByMooringCategoriesAndAvailability(List<MooringCategory> mooringCategories, Date startDate, Date endDate){

        return bookingRepository.findByMooringCategoriesAndAvailability(mooringCategories,startDate,endDate);

    }




}
