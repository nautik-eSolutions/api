package com.nautik.api.dto.mooring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringDto implements Serializable {
    Long id;
    String number;
    MooringCategoryDto mooringCategory;
}