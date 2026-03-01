package com.nautik.api.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArrivalStatusDto {
    private Boolean hasArrived;
    private String actualTime;

}
