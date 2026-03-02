package com.nautik.api.dto.mooring.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFixedMooringRequestDto {
    private Integer portId;
    private String mooringNumber;
    private String message;
}