package com.nautik.api.dto.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortInfoDto {
    private Integer id;
    private String name;
    private String cityName;
    private String companyName;
    private Integer vhfChannel;
    private Double maxBoatLength;
    private Double maxBoatBeam;
    private Double maxBoatDraft;
    private Integer totalMoorings;
    private Boolean fuelStation;
    private Boolean travelLift;
    private Boolean crane;
    private Double lon;
    private Double lat;
    private String phoneNumber;
    private String email;
    private String openingHours;

}
