package com.nautik.api.dto.bookings;

import com.nautik.api.domain.booking.BookingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingListItemDto {
    private Integer id;
    private Date startDate;
    private Date endDate;
    private Double totalCost;
    private BookingStatus status;
    private String boatName;
    private String boatRegistryNumber;
    private String clientName;
    private String clientEmail;
    private Integer portId;
}