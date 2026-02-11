package com.nautik.api.dto.bookings;


import lombok.Data;

@Data
public class BookingRequestDto {

    private Integer mooringCategoryId;
    private String startDate;
    private  String endDate;
    private Integer boatId;
}
