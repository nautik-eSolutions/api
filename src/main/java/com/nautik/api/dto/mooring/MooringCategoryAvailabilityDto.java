package com.nautik.api.dto.mooring;

import lombok.Data;

import java.util.Date;

@Data
public class MooringCategoryAvailabilityDto {

    private Integer id;
    //ZoneDto zone;

    private String zonePortName;
    private String zoneName;
    private int dimensionsMaxBeam;
    private int dimensionsMaxLength;
    private Date startDate;
    private Date endDate;
    private double basePrice;
    private double tax = basePrice * 0.21;
    private double totalPrice = basePrice * 1.21;

}
