package com.nautik.api.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingReassignmentDto {
    private Integer bookingId;
    private Integer oldMooringId;
    private Integer newMooringId;
    private Date startDate;
    private Date endDate;


}
