package com.nautik.api.dto.mooring;

import lombok.Data;

@Data
public class MooringCategoryInfoDto {

    private Integer id;
    private Integer zoneId;
    private Integer dimensionsId;
    private int minPricePerDay;
    private String name;
}
