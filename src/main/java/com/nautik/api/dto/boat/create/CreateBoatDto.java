package com.nautik.api.dto.boat.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBoatDto {
    String name;
    String registryNumber;
    Double length;
    Double beam;
    Double draft;
    String boatType;
}
