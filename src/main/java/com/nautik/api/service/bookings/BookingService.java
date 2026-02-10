package com.nautik.api.service.bookings;


import com.nautik.api.domain.booking.Booking;
import com.nautik.api.dto.bookings.BookingDto;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final MooringRepository mooringRepository;
    private final MooringCategoryRepository mooringCategoryRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;



    public List<BookingDto> getAvailableMooringCategoriesByPortAndStartDateAndEndDate(Integer mooringCategoryId, String stringStartDate, String stringEndDate) {
        Date startDate = dateFormater(stringStartDate);
        Date endDate = dateFormater(stringEndDate);


        List<Booking> bookings = bookingRepository.findAllByMooringMooringCategoryIdAndStartDateBeforeAndEndDateAfter(mooringCategoryId, endDate, startDate);


        return bookings.stream().map(booking->modelMapper.map(booking, BookingDto.class)).toList();
    }


    private Date dateFormater(String dateString)  {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatter.parse(dateString);
        }catch (ParseException ignored){

        }


        return new Date();
    }


}
