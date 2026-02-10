package com.nautik.api.service.bookings;


import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.repository.moorings.MooringCategoryRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MooringBookingService {

    private final MooringRepository mooringRepository;
    private final MooringCategoryRepository mooringCategoryRepository;
    private final

    public List<MooringCategory> getAvailableMooringCategoriesByPortAndStartDateAndEndDate(Integer portId, String stringStartDate, String stringEndDate){
     Date startDate;
     Date endDate;

     try{
         startDate = dateFormater(stringStartDate);
         endDate = dateFormater(stringEndDate);
     }catch (ParseException parseException){

     }



        return new ArrayList<MooringCategory>();
    }







    private Date dateFormater(String dateString) throws ParseException {
        SimpleDateFormat formatter =  new SimpleDateFormat("dd-MM-yyyy");

        return formatter.parse(dateString);
    }



}
