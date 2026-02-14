package com.nautik.api.dto.boat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Value
public class BoatTypeDto implements Serializable {
    Integer id;
    String name;


    @Override
    public String toString() {
        return name;
    }
}