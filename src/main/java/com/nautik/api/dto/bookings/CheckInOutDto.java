package com.nautik.api.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInOutDto {
    private Integer id;
    private String guestName;
    private String boatName;
    private String mooringNumber;
    private String scheduledTime;
    private String actualTime;
    private Boolean hasArrived;
    private Integer bookingId;
}
