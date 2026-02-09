package com.nautik.api.dto.mooring;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.dto.location.ZoneDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringCategoryDto {
        private Integer id;
    //ZoneDto zone;

     private String ZoneName;
     private int dimensionsMaxBeam;
     private int dimensionsMaxLength;




}