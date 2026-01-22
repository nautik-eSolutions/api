package com.nautik.api.dto.mooring;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.nautik.api.domain.moorings.Mooring}
 */
@Value
public class MooringDto implements Serializable {
    Long id;
    Long number;
    MooringCategoryDto mooringCategory;
}