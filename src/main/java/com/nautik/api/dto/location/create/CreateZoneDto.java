package com.nautik.api.dto.location.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateZoneDto {
    String name;
    String description;
    String portName;
    List<Integer> mooringCategoriesMooringNumber;
}
