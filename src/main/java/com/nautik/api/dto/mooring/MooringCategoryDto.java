package com.nautik.api.dto.mooring;

import com.nautik.api.dto.location.ZoneDto;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.nautik.api.domain.moorings.MooringCategory}
 */
@Value
public class MooringCategoryDto implements Serializable {
    Integer id;
    ZoneDto zone;
    Long maxLength;
    Long maxBeam;
}