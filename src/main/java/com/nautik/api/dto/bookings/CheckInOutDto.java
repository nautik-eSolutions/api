package com.nautik.api.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInOutDto {
    private Integer id;
    private String guestName;
    private String boatName;
    private String mooringNumber;
    private Date scheduledTime;
    private Date actualTime;
    private Boolean hasArrived;
    private Integer bookingId;
}
