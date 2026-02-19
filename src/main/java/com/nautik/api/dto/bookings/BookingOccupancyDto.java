package com.nautik.api.dto.bookings;

import lombok.Data;

@Data
public class BookingOccupancyDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long boatId;
    private Long bookingStatusId;
    private int mooringNumber;
    private int mooringId;
}
