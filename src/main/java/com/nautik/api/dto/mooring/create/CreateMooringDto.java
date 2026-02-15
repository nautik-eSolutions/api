package com.nautik.api.dto.mooring.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateMooringDto {
    private Long number;
    private Integer zoneId;
    private Integer dimensionsId;
}
