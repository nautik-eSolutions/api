package com.nautik.api.service.bookings;


import com.nautik.api.domain.Boat;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.booking.BookingStatus;
import com.nautik.api.domain.booking.CheckInOut;
import com.nautik.api.domain.exceptions.*;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.dto.bookings.BookingOccupancyDto;
import com.nautik.api.repository.boat.BoatRepository;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.bookings.CheckInOutRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ReassignmentService reassignmentService;


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

    public Page<BookingDto> getBookingsByPort(Integer portId, String search, Pageable pageable) {
        Page<Booking> bookingsPage;
            bookingsPage = bookingRepository.findByPortId(portId, pageable);

        return bookingsPage.map(this::mapToBookingDto);
    }
    public BookingDto getBookingById(Integer id, Integer portId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        Integer bookingPortId = bookingRepository.getPortIdByBookingId(id);

        if (!bookingPortId.equals(portId)) {
            throw new ForbiddenException("You do not have access to this booking");
        }
        return mapToBookingDto(booking);
    }

    public void cancelBooking(Integer id, Integer portId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException("Booking is already cancelled");
        }

        Integer bookingPortId = bookingRepository.getPortIdByBookingId(id);

        if (!bookingPortId.equals(portId)) {
            throw new ForbiddenException("You do not have access to this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        Integer mooringCategoryId = booking.getMooring().getMooringCategory().getId();
        reassignmentService.reassignBookings(mooringCategoryId);
    }
    public BookingDto updateBookingStatus(Integer id, BookingStatus newStatus, Integer portId) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found "));
        Integer bookingPortId = bookingRepository.getPortIdByBookingId(id);

        if (!bookingPortId.equals(portId)) {
            throw new ForbiddenException("You do not have access to this booking");
        }
        if (newStatus == BookingStatus.CANCELLED) {
            throw new InvalidStatusTransitionException("Cannot change to CANCELLED.");
        }
        booking.setStatus(newStatus);
        booking = bookingRepository.save(booking);
        return mapToBookingDto(booking);
    }



    private BookingDto mapToBookingDto(Booking booking) {
        BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);
        bookingDto.setBoatName(booking.getBoat().getName());
        bookingDto.setBoatRegistryNumber(booking.getBoat().getRegistryNumber());
        bookingDto.setClientName(booking.getBoat().getUser().getFirstName() + " " + booking.getBoat().getUser().getLastName());
        bookingDto.setClientEmail(booking.getBoat().getUser().getEmail());
        bookingDto.setPortId(booking.getMooring().getMooringCategory().getZone().getPort().getId());
        return bookingDto;
    }


    public List<BookingDto> getAllBookingsByMooringId(Integer mooringId){
        List <Booking> bookings = bookingRepository.findAllByMooringId(mooringId);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No bookings we're found");
        }

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
