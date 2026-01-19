package com.nautik.api.dto.boat;

import com.nautik.api.dto.user.UserDto;
import lombok.*;

import java.io.Serializable;


@Data
public class BoatDto {
    Integer id;
    String name;
    String registryNumber;
    Double length;
    Double beam;
    Double draft;
//d
}