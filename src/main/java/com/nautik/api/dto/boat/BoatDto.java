package com.nautik.api.dto.boat;

import com.nautik.api.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Value
public class BoatDto implements Serializable {
    Integer id;
    String name;
    String registryNumber;
    Double length;
    Double beam;
    Double draft;
    BoatTypeDto boatType;
    UserDto user;
}