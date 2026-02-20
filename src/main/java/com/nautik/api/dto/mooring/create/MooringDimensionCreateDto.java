package com.nautik.api.dto.mooring.create;

import lombok.Data;

@Data
public class MooringDimensionCreateDto {
    private String name;
    private Double length;
    private Double beam;
    private Double draft;

}
