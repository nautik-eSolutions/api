package com.nautik.api.dto.port;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortInfoDto {
    private Integer id;
    private String name;
    private String cityName;
    private String companyName;
    private Integer vhfChannel;
    private Double maxBoatLength;
    private Double maxBoatBeam;
    private Double maxBoatDraft;
    private Integer totalBerths;
    private Boolean fuelStation;
    private Boolean travelLift;
    private Boolean crane;
    private String phone;
    private String email;
    private String openingHours;

}
