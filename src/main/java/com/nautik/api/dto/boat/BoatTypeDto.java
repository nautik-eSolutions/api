package com.nautik.api.dto.boat;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoatTypeDto implements Serializable {
    Integer id;
    String name;


    @Override
    public String toString() {
        return name;
    }
}