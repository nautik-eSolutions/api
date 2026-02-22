package com.nautik.api.dto.mooring;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringDimensionDto implements Serializable {
    private Integer id;
    private int maxLength;
    private int maxBeam;
    private int maxDraft;
    private String name;
}