package com.nautik.api.dto.mooring.create;

import lombok.Data;

@Data
public class MooringDimensionCreateDto {
    private String name;
    private Double maxLength;
    private Double maxBeam;
    private Double maxDraft;

}
