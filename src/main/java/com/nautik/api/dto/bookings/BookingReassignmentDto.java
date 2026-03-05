package com.nautik.api.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingReassignmentDto {
    private Integer bookingId;
    private Integer oldMooringId;
    private Integer newMooringId;
    private Date arrival;
    private Date departure;


}
