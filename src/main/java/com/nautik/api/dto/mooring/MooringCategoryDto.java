package com.nautik.api.dto.mooring;

import com.nautik.api.dto.location.ZoneDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringCategoryDto implements Serializable {
    Integer id;
//    ZoneDto zone;
    Long maxLength;
    Long maxBeam;
}