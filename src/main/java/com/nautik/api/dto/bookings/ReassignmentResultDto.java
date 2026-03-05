package com.nautik.api.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReassignmentResultDto {
    private Integer totalReassigned;
    private Integer totalBookings;
    private Integer categoryId;
    private List<BookingReassignmentDto> reassignments;
}
