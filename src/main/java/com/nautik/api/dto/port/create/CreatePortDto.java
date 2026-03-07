package com.nautik.api.dto.port.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortDto {
    private Integer id;
    private String name;
    private Integer vhfChannel;
    private String cityName;
    private Boolean fuelStation;
    private Boolean travelLift;
    private Boolean crane;
    private Double lon;
    private Double lat;
    private String phoneNumber;
    private String email;
    private String openingHours;
}
