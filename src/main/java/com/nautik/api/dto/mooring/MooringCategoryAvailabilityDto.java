package com.nautik.api.dto.mooring;

import com.nautik.api.dto.location.ZoneDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class MooringCategoryAvailabilityDto {

    private Integer id;
    //ZoneDto zone;

    private String zonePortName;
    private String zoneName;
    private int dimensionsMaxBeam;
    private int dimensionsMaxLength;
    private String startDate;
    private String endDate;
    private double minPricePerDay;
    private double basePrice;
    private double tax ;
    private double totalPrice;
    private ZoneDto zone;

}
